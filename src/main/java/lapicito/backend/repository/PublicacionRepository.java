package lapicito.backend.repository;

import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Publicacion;
import lapicito.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {
    List<Publicacion> findByUsuario(Usuario usuario);

    List<Publicacion> findByEspacio(Espacio espacio);

    @Query(nativeQuery = true,
            value = "SELECT p.*" +
                    "FROM publicacion p INNER JOIN espacio e ON p.id_espacio = e.id " +
                    "WHERE p.id_espacio IN (:id_espacio) ORDER BY p.fecha_alta DESC ")
    List<Publicacion>findByEspacioOrdenado(@Param("id_espacio") int[] id_espacio);

    @Query(nativeQuery = true,
            value = "SELECT p.* FROM publicacion p INNER JOIN espacio e ON p.id_espacio = e.id \n" +
                    "where (p.titulo like %?1% or p.descripcion like %?1% or e.titulo like %?1%\n" +
                    "or e.descripcion like %?1%) and p.id_espacio = ?2 ORDER by p.fecha_alta ASC ;")
    List<Publicacion> search(String value, @Param("id_espacio") int id_espacio);
}
