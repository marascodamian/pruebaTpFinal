package lapicito.backend.dto;

public class ViewLapicitoDto {

    private int cantidad;
    private UsuarioPerfilDto usuarioEmisor;
    private String comentario;

    public ViewLapicitoDto() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public UsuarioPerfilDto getUsuarioEmisor() {
        return usuarioEmisor;
    }

    public void setUsuarioEmisor(UsuarioPerfilDto usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
