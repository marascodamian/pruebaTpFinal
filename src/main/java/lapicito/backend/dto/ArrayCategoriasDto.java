package lapicito.backend.dto;

import java.util.List;
import java.util.Set;

public class ArrayCategoriasDto {

    private Set<Integer> categorias;

    public Set<Integer> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Integer> categorias) {
        this.categorias = categorias;
    }
}
