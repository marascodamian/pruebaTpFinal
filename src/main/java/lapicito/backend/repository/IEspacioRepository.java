package lapicito.backend.repository;

import lapicito.backend.dto.ArrayCategoriasDto;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import java.util.List;


@Repository
public interface IEspacioRepository extends JpaRepository<Espacio, Integer> {

    List<Espacio> findByCategoria(Categoria categoria);
    void deleteById(int id);
    @Query(nativeQuery = true,
            value = "SELECT e.* FROM espacio e \n" +
                    "where (e.titulo like %?1% \n" +
                    "or e.descripcion like %?1% ) and e.id = ?2 ;")
    List<Espacio> search(String value, @Param("id_espacio") int id_espacio);
}