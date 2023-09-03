package lapicito.backend.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Lapicito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int cantidad;
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss.SSS")
    private Calendar fecha;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "id_usuario_receptor")
    private Usuario usuarioReceptor;

    @ManyToOne
    @JoinColumn(name = "id_usuario_emisor")
    private Usuario usuarioEmisor;

    public Lapicito() {
    }

    public Lapicito(int cantidad, Usuario usuarioReceptor, Usuario usuarioEmisor, Calendar fecha, String comentario) {
        this.cantidad = cantidad;
        this.usuarioReceptor = usuarioReceptor;
        this.usuarioEmisor = usuarioEmisor;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }

    public Usuario getUsuarioEmisor() {
        return usuarioEmisor;
    }

    public void setUsuarioEmisor(Usuario usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
