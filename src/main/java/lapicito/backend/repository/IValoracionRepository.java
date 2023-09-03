package lapicito.backend.repository;

import lapicito.backend.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IValoracionRepository extends JpaRepository<Valoracion, Integer> {

    boolean existsValoracionByUsuarioIdAndPublicacionId(int intU, int intP);
    List<Valoracion> getValoracionByPublicacionId(int id);

}
