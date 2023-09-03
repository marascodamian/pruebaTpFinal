package lapicito.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lapicito.backend.entity.Rol;
import lapicito.backend.security.enums.RolNombre;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
    boolean existsByRolNombre(RolNombre rolNombre);
}
