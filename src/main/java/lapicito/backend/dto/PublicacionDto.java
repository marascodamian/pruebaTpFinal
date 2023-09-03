package lapicito.backend.dto;

public class PublicacionDto {
    private String titulo;
    private String descripcion;
    private Boolean es_anuncio;
    private Boolean activo;
    private int id_usuario;
    private int id_espacio;

    public PublicacionDto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Boolean getEs_anuncio() {
        return es_anuncio;
    }

    public void setEs_anuncio(Boolean es_anuncio) {
        this.es_anuncio = es_anuncio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_espacio() {
        return id_espacio;
    }

    public void setId_espacio(int id_espacio) {
        this.id_espacio = id_espacio;
    }
}
