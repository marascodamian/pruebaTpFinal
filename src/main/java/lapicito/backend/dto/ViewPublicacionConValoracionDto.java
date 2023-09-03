package lapicito.backend.dto;

import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Valoracion;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.List;

public class ViewPublicacionConValoracionDto {

    private int idPublicacion;
    private String titulo;
    private String descripcion;
    private String url_adjunto;
    private Boolean es_anuncio;
    private Boolean activo;
    private UsuarioPerfilDto usuario;
    private ViewEspacioDto espacio;
    private List<ViewValoracionDto> valoracionDtoList;
    private double promedioValoracion;
    private Calendar fecha_alta;
    private int cantidadDeDescargas;
    private String aws_url_image = "https://lapicito-bucket.s3.us-east-2.amazonaws.com/";

    public ViewPublicacionConValoracionDto() {
    }

    public int getCantidadDeDescargas() {
        return cantidadDeDescargas;
    }

    public void setCantidadDeDescargas(int cantidadDeDescargas) {
        this.cantidadDeDescargas = cantidadDeDescargas;
    }

    public List<ViewValoracionDto> getValoracionDtoList() {
        return valoracionDtoList;
    }

    public void setValoracionDtoList(List<ViewValoracionDto> valoracionDtoList) {
        this.valoracionDtoList = valoracionDtoList;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
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
        return this.aws_url_image +url_adjunto;
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

    public UsuarioPerfilDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPerfilDto usuario) {
        this.usuario = usuario;
    }

    public ViewEspacioDto getEspacio() {
        return espacio;
    }

    public void setEspacio(ViewEspacioDto espacio) {
        this.espacio = espacio;
    }

    public double getPromedioValoracion() {
        return promedioValoracion;
    }

    public Calendar getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Calendar fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public void setPromedioValoracion(double promedioValoracion) {
        this.promedioValoracion = promedioValoracion;
    }
}

