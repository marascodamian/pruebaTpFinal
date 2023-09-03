package lapicito.backend.entity;

import com.sun.istack.NotNull;
import lapicito.backend.enums.PermisoNombre;
import lapicito.backend.security.enums.RolNombre;

import javax.persistence.*;

@Entity
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "descripcion")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PermisoNombre permisoNombre;

    public Permiso() {
    }

    public Permiso(PermisoNombre permisoNombre) {
        this.permisoNombre = permisoNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PermisoNombre getPermisoNombre(PermisoNombre roleUser) {
        return permisoNombre;
    }

    public void setPermisoNombre(PermisoNombre permisoNombre) {
        this.permisoNombre = permisoNombre;
    }
}
