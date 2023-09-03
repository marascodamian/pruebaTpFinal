package lapicito.backend.entity;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Value;
/*import lapicito.backend.entity.Categoria;*/

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //si se puede extraer nombre y apellido de google/facebook puede ser @notNull
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    //@Column(unique = true)
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String telefono;
    private String avatar_url;
    private String portada_url;
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha_alta;
    private Integer cantidad_lapicitos;
    @NotNull
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="usuario_rol",joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @ManyToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinTable(name="usuario_categoria",joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Categoria> categorias = new HashSet<>();

    /*@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MPUser mpUser;*/

    @JoinColumn(name = "mpUser_id", referencedColumnName = "id")
    @OneToOne
    private MPUser mpUser;


    public Usuario(){

    }

    public Usuario(String password, String email) {
        this.password = password;
        this.email = email;
    }


    public Usuario(String nombre, String apellido, String userName, String password,String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }


    public MPUser getMpUser() {
        return mpUser;
    }

    public void setMpUser(MPUser mpUser) {
        this.mpUser = mpUser;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
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

    public String getPassword() {
        return password;
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
        return  avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPortada_url() {
        return   portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public Calendar getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Calendar fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public int getCantidad_lapicitos() {
        return cantidad_lapicitos;
    }

    public void setCantidad_lapicitos(int cantidad_lapicitos) {
        this.cantidad_lapicitos = cantidad_lapicitos;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }






}
