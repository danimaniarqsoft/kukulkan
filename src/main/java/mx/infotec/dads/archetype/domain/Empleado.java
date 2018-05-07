package mx.infotec.dads.archetype.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Departamento departamento;

    @OneToOne
    @JoinColumn(unique = true)
    private Direccion direccion;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trabajo> trabajos = new HashSet<>();

    @ManyToOne
    private Empleado manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Empleado name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Empleado departamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Empleado direccion(Direccion direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Set<Trabajo> getTrabajos() {
        return trabajos;
    }

    public Empleado trabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
        return this;
    }

    public Empleado addTrabajo(Trabajo trabajo) {
        this.trabajos.add(trabajo);
        trabajo.setEmpleado(this);
        return this;
    }

    public Empleado removeTrabajo(Trabajo trabajo) {
        this.trabajos.remove(trabajo);
        trabajo.setEmpleado(null);
        return this;
    }

    public void setTrabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public Empleado getManager() {
        return manager;
    }

    public Empleado manager(Empleado empleado) {
        this.manager = empleado;
        return this;
    }

    public void setManager(Empleado empleado) {
        this.manager = empleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Empleado empleado = (Empleado) o;
        if (empleado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
