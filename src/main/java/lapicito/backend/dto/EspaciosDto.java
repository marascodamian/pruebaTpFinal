package lapicito.backend.dto;

import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Permiso;
import lapicito.backend.entity.Usuario;
import org.springframework.web.multipart.MultipartFile;

public class EspaciosDto {

    private int id_usuario;
    private String titulo;
    private String descripcion;
    private String portada_url;
    private int id_categoria;
    private String aws_url_image = "https://lapicito-bucket.s3.us-east-2.amazonaws.com/";


    public EspaciosDto(int id_usuario, String titulo, String descripcion, String portada_url, int id_categoria) {
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.portada_url = portada_url;
        this.id_categoria = id_categoria;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada_url() {
        return this.aws_url_image + portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
