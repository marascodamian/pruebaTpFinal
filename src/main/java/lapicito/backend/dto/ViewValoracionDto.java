package lapicito.backend.dto;

import java.util.Calendar;

public class ViewValoracionDto {

    private Calendar fecha;
    private String comentario;
    private String nombreUsuario;
    private int cantidad_estrellas;

    public ViewValoracionDto(){

    }

    public ViewValoracionDto(Calendar fecha, String comentario, String nombreUsuario, int cantidad_estrellas) {
        this.fecha = fecha;
        this.comentario = comentario;
        this.nombreUsuario = nombreUsuario;
        this.cantidad_estrellas = cantidad_estrellas;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidad_estrellas() {
        return cantidad_estrellas;
    }

    public void setCantidad_estrellas(int cantidad_estrellas) {
        this.cantidad_estrellas = cantidad_estrellas;
    }
}
