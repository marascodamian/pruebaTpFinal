package lapicito.backend.dto;

import lapicito.backend.entity.Categoria;

public class ViewEspacioDto {

    private int idEspacio;
    private String titulo;
    private String descripcion;
    private String portada_url;
    private Categoria categoria;
    private double promedioValoraciones;
    private UsuarioPerfilDto usuarioPerfilDto;
    private int cantidadMiembrosEspacio;
    private String aws_url_image = "https://lapicito-bucket.s3.us-east-2.amazonaws.com/";

    public ViewEspacioDto(){

    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
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

    public String getPortada_url() {
        return this.aws_url_image+ portada_url;
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

    public double getPromedioValoraciones() {
        return promedioValoraciones;
    }

    public void setPromedioValoraciones(double promedioValoraciones) {
        this.promedioValoraciones = promedioValoraciones;
    }

    public UsuarioPerfilDto getUsuarioPerfilDto() {
        return usuarioPerfilDto;
    }

    public void setUsuarioPerfilDto(UsuarioPerfilDto usuarioPerfilDto) {
        this.usuarioPerfilDto = usuarioPerfilDto;
    }

    public int getCantidadMiembrosEspacio() {
        return cantidadMiembrosEspacio;
    }

    public void setCantidadMiembrosEspacio(int cantidadMiembrosEspacio) {
        this.cantidadMiembrosEspacio = cantidadMiembrosEspacio;
    }
}
