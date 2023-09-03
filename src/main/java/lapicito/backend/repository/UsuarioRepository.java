package lapicito.backend.repository;

import lapicito.backend.dto.ArrayCategoriasDto;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.MPUser;
import org.springframework.data.jpa.repository.JpaRepository;
import lapicito.backend.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario>findByUserName(String userName);
    Optional<Usuario>findByEmail(String email);
    Optional<Usuario>findById(int usuario_id);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Usuario findByMpUser(MPUser mpUser);
}
