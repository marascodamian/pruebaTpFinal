package lapicito.backend.service;

import lapicito.backend.entity.Valoracion;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface IValoracionService {

    void save(Valoracion valoracion);
    boolean existsValoracionByUsuarioIdAndPublicacionId(int id_U, int id_P);
    List<Valoracion> getValoracionByPublicacionId(int id);
    double getPromedioValoraciones(int id);
    double getPromedioValoracionesByEspacio(int id);

}
