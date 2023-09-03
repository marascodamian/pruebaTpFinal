package lapicito.backend.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private String descripcion;
    private String url_adjunto;
    private Boolean es_anuncio;
    private Boolean activo;
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha_alta;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio")
    private Espacio espacio;

    public Publicacion(){

    }

    public Publicacion(String titulo, String descripcion, String url_adjunto,
                       Boolean es_anuncio, Boolean activo, Calendar fecha_alta,
                       Usuario usuario, Espacio espacio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_adjunto = url_adjunto;
        this.es_anuncio = es_anuncio;
        this.activo = activo;
        this.fecha_alta = fecha_alta;
        this.usuario = usuario;
        this.espacio = espacio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl_adjunto() {
        return url_adjunto;
    }

    public void setUrl_adjunto(String url_adjunto) {
        this.url_adjunto = url_adjunto;
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

    public Calendar getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Calendar fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }
}
