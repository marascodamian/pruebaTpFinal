package lapicito.backend.dto;

import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Usuario;

public class UEPDto {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String userName;
    private String email;
    private String avatar_url;
    private String aws_url_image = "https://lapicito-bucket.s3.us-east-2.amazonaws.com/";
    private ViewEspacioDto viewEspacioDto;

    public UEPDto(int idUsuario, String nombre, String apellido, String userName, String email, String avatar_url, ViewEspacioDto viewEspacioDto) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
        this.email = email;
        this.avatar_url = avatar_url;
        this.viewEspacioDto = viewEspacioDto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar_url() {
        return this.aws_url_image + avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public ViewEspacioDto getViewEspacioDto() {
        return viewEspacioDto;
    }

    public void setViewEspacioDto(ViewEspacioDto viewEspacioDto) {
        this.viewEspacioDto = viewEspacioDto;
    }


}

