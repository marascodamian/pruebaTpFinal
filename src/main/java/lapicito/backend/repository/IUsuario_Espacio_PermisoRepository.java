package lapicito.backend.repository;

import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Permiso;

import lapicito.backend.entity.Usuario;
import lapicito.backend.entity.Usuario_Espacio_Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUsuario_Espacio_PermisoRepository extends JpaRepository<Usuario_Espacio_Permiso, Integer> {

    //@Query(value = "select * from usuario_espacio_permiso where usuario_espacio_permiso.id_usuario =:id", nativeQuery = true)
    //List<Usuario_Espacio_Permiso> findByIdUsuario(@Param ("id") int id);
    List<Usuario_Espacio_Permiso> findAllByUsuarioId(int id);
    void deleteByEspacioId(int id);
    List<Usuario_Espacio_Permiso> findAllByEspacioId(int id);
    List<Usuario_Espacio_Permiso> findAllByEspacioCategoria_Id(int id);
    //void deleteUsuario_Espacio_PermisoByEspacioId(int id);
    Usuario_Espacio_Permiso findByEspacioId(int id);

    @Query(nativeQuery = true, value = "SELECT * FROM usuario_espacio_permiso where id_espacio = :id and id_permiso = 1")
    Usuario_Espacio_Permiso findUEPByIdEspacio(@Param ("id") int id);
    List<Usuario_Espacio_Permiso> findAllByEspacioIdAndPermiso(int id_espacio, Permiso permiso);
    boolean existsByUsuarioAndEspacioIdAndPermiso(Usuario usuario, int id, Permiso permiso);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM usuario_espacio_permiso uep where uep.id_espacio = ?1 " +
            "and uep.id_usuario = ?2 and id_permiso = 2")
    void delete(int id_espacio,int id_usuario);
}
