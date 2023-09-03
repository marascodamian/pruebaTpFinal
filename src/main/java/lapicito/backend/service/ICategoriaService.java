
package lapicito.backend.service;

import lapicito.backend.entity.Categoria;

import java.util.List;

public interface ICategoriaService {

    List<Categoria> listarCategoria();
    void saveByAdmin(Integer idUsuario, Categoria categoria);
    boolean existById(Integer id);
}
