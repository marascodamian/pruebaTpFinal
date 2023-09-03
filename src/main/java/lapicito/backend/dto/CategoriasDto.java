package lapicito.backend.dto;

import lapicito.backend.entity.Categoria;

import java.util.Set;

public class CategoriasDto {

    private int id_usuario;
    private Set<Categoria> categorias;

    public CategoriasDto(int id_usuario, Set<Categoria> categorias) {
        this.id_usuario = id_usuario;
        this.categorias = categorias;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
}
