package lapicito.backend.entity;


import javax.persistence.*;

@Entity
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String descripcion;
    private String portada_url;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;


    public Espacio(String titulo, String descripcion, String portada_url, Categoria categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.portada_url = portada_url;
        this.categoria = categoria;
    }

    public Espacio() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada_url() {
        return portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
