package lapicito.backend.service;

import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Usuario_Espacio_Permiso;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public interface IUsuario_Espacio_PermisoService {

    List<Usuario_Espacio_Permiso> getUEPByIdEspacio(int id);
    List<Usuario_Espacio_Permiso> getUEPByCategoriasUsuario(int id);

    void saveSeguidor(int espacio_id, int usuario_id);

    int getCantidadSeguidores(int id);

    boolean getSigueEspacio(int id_espacio, int id_usuario);

    void deleteSeguidor(int espacio_id, int usuario_id);
}
