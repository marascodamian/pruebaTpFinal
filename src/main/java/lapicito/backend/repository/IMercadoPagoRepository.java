package lapicito.backend.repository;

import lapicito.backend.entity.MPUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IMercadoPagoRepository extends JpaRepository<MPUser, Integer> {

    boolean existsById(int id);
}
