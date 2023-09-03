package lapicito.backend.dto;

import com.sun.istack.NotNull;
import lapicito.backend.entity.Categoria;
import lapicito.backend.entity.Espacio;
import lapicito.backend.entity.Rol;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class UsuarioPerfilDto {
    private int id;
    private String nombre;
    private String apellido;
    private String userName;
    private String email;
    private String telefono;
    private String avatar_url;
    private String portada_url;
    private Integer cantidad_lapicitos;
    private Set<Rol> roles;
    private Set<Categoria> categorias;
    private double promedioUsuario;
    private Integer mp_user_id;
    private String aws_url_image = "https://lapicito-bucket.s3.us-east-2.amazonaws.com/";

    public UsuarioPerfilDto(int id, String nombre, String apellido, String userName, String email, String telefono, String avatar_url, String portada_url, Integer cantidad_lapicitos, Set<Rol> roles, Set<Categoria> categorias, double promedioUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
        this.email = email;
        this.telefono = telefono;
        this.avatar_url = avatar_url;
        this.portada_url = portada_url;
        this.cantidad_lapicitos = cantidad_lapicitos;
        this.roles = roles;
        this.categorias = categorias;
        this.promedioUsuario = promedioUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar_url() {
        return this.aws_url_image + avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPortada_url() {
        return this.aws_url_image + portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public Integer getCantidad_lapicitos() {
        return cantidad_lapicitos;
    }

    public void setCantidad_lapicitos(Integer cantidad_lapicitos) {
        this.cantidad_lapicitos = cantidad_lapicitos;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public double getPromedioUsuario() {
        return promedioUsuario;
    }

    public void setPromedioUsuario(double promedioUsuario) {
        this.promedioUsuario = promedioUsuario;
    }

    public Integer getMp_user_id() {
        return mp_user_id;
    }

    public void setMp_user_id(Integer mp_user_id) {
        this.mp_user_id = mp_user_id;
    }
}
