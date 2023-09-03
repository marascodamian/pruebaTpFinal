package lapicito.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario_Espacio_Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NotNull
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio")
    @NotNull
    private Espacio espacio;

    @ManyToOne
    @JoinColumn(name = "id_permiso")
    @NotNull
    private Permiso permiso;

    public Usuario_Espacio_Permiso() {
    }

    public Usuario_Espacio_Permiso(Usuario usuario, Espacio espacio, Permiso permiso) {
        this.usuario = usuario;
        this.espacio = espacio;
        this.permiso = permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
