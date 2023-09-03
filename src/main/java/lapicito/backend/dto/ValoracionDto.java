package lapicito.backend.dto;

import java.util.Calendar;


public class ValoracionDto {

    private String comentario;
    private int id_usuario;
    private int id_publicacion;
    private int cantidad_estrellas;

    public ValoracionDto(){

    }

    public ValoracionDto( String comentario, int id_usuario, int id_publicacion, int cantidad_estrellas) {

        this.comentario = comentario;
        this.id_usuario = id_usuario;
        this.id_publicacion = id_publicacion;
        this.cantidad_estrellas = cantidad_estrellas;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public int getCantidad_estrellas() {
        return cantidad_estrellas;
    }

    public void setCantidad_estrellas(int cantidad_estrellas) {
        this.cantidad_estrellas = cantidad_estrellas;
    }
}
