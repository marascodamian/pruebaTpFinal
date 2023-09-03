package lapicito.backend.entity;

import javax.persistence.*;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcion;
    private String icono;
    
    public Categoria(){

    }

    public int getIdCategoria() {
        return id;
    }

    public void setIdCategoria(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String foto_url) {
        this.icono = foto_url;
    }
}
