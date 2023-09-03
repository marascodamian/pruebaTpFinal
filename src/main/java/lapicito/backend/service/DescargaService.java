package lapicito.backend.service;

import lapicito.backend.entity.Descarga;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import lapicito.backend.repository.DescargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DescargaService {

    @Autowired
    DescargaRepository descargaRepository;

    @Autowired
    IPublicacionService publicacionService;

    @Autowired
    UsuarioService usuarioService;

    public void save(Descarga descarga){
        try{
            descargaRepository.save(descarga);
        }catch (Exception e){
            System.out.println("error guardando descarga");
            e.getMessage();
        }

    }

    public List<Descarga> findByIdPublicacion(int id_publicacion) {
        Publicacion publicacion = publicacionService.findById(id_publicacion);
        return descargaRepository.findAllByPublicacion(publicacion);
    }

    public List<Descarga> findByIdUsuario(int id_usuario) {
        Usuario usuario = usuarioService.getById(id_usuario).get();
        return descargaRepository.findAllByUsuario(usuario);
    }
}
