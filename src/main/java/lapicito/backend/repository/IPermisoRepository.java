package lapicito.backend.repository;

import lapicito.backend.entity.Permiso;
import lapicito.backend.enums.PermisoNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPermisoRepository extends JpaRepository<Permiso, Integer> {

    Permiso findByPermisoNombre(PermisoNombre permisoNombre);
}
