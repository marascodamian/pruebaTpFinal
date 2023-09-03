package lapicito.backend.repository;

import lapicito.backend.entity.Descarga;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescargaRepository extends JpaRepository<Descarga,Integer> {
    List<Descarga> findAllByPublicacion(Publicacion publicacion);
    List<Descarga> findAllByUsuario(Usuario usuario);
}
