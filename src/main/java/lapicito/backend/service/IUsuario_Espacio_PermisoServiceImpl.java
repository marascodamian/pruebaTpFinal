package lapicito.backend.service;

import lapicito.backend.entity.*;
import lapicito.backend.enums.PermisoNombre;
import lapicito.backend.repository.IUsuario_Espacio_PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class IUsuario_Espacio_PermisoServiceImpl implements IUsuario_Espacio_PermisoService {

    @Autowired
    IUsuario_Espacio_PermisoRepository usuario_espacio_permisoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EspacioServiceImpl espacioService;

    @Autowired
    PermisoService permisoService;



    @Override
    public List<Usuario_Espacio_Permiso> getUEPByIdEspacio(int id){
        List<Usuario_Espacio_Permiso> uep = usuario_espacio_permisoRepository.findAllByEspacioId(id);
        return uep;
    }

    @Override
    public List<Usuario_Espacio_Permiso> getUEPByCategoriasUsuario(int id){

        Set<Categoria> categorias = usuarioService.getCategoriasByIdUsuario(id);
        List<Usuario_Espacio_Permiso> uep = new ArrayList();

        for (Categoria itemCategorias: categorias) {
            List<Usuario_Espacio_Permiso> nuevoUEP = usuario_espacio_permisoRepository.findAllByEspacioCategoria_Id(itemCategorias.getIdCategoria());
            for(Usuario_Espacio_Permiso itemUep: nuevoUEP){
                uep.add(itemUep);
            }
        }
        return uep;
    }

    @Override
    public void saveSeguidor(int espacio_id, int usuario_id) {


        Usuario_Espacio_Permiso uep =
                new Usuario_Espacio_Permiso(
                        usuarioService.getById(usuario_id).get(),
                        espacioService.getById(espacio_id).get(),
                        permisoService.getPermisoByNombre(PermisoNombre.ROLE_USER));
        try{
            usuario_espacio_permisoRepository.save(uep);
        }catch (Exception e){
            System.out.println("Error al guardar uep");
            e.getMessage();
        }
    }

    @Override
    public int getCantidadSeguidores(int id) {

        List<Usuario_Espacio_Permiso> usuarioEspacioPermisos =
                usuario_espacio_permisoRepository.findAllByEspacioIdAndPermiso(id,
                        permisoService.getPermisoByNombre(PermisoNombre.ROLE_USER));
        return usuarioEspacioPermisos.size();
    }

    @Override
    public boolean getSigueEspacio(int id_espacio, int id_usuario) {

        Usuario usuario = usuarioService.getById(id_usuario).get();

        return usuario_espacio_permisoRepository.existsByUsuarioAndEspacioIdAndPermiso(
                usuario,id_espacio,permisoService.getPermisoByNombre(PermisoNombre.ROLE_USER));
    }

    @Override
    public void deleteSeguidor(int espacio_id, int usuario_id) {
        try{

            usuario_espacio_permisoRepository.delete(espacio_id,usuario_id);
        }catch (Exception e){
            System.out.println("Error al borrar uep");
            e.getMessage();
        }
    }

}
