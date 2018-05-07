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
 * A Departamento.
 */
@Entity
@Table(name = "departamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "departamento")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Empleado> empleados = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Departamento name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public Departamento empleados(Set<Empleado> empleados) {
        this.empleados = empleados;
        return this;
    }

    public Departamento addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
        empleado.setDepartamento(this);
        return this;
    }

    public Departamento removeEmpleado(Empleado empleado) {
        this.empleados.remove(empleado);
        empleado.setDepartamento(null);
        return this;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Departamento departamento = (Departamento) o;
        if (departamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Departamento{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
