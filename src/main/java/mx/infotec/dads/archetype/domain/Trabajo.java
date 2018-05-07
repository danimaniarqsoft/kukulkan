package mx.infotec.dads.archetype.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Trabajo.
 */
@Entity
@Table(name = "trabajo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Empleado empleado;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "trabajo_tarea",
               joinColumns = @JoinColumn(name="trabajos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tareas_id", referencedColumnName="id"))
    private Set<Tarea> tareas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Trabajo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Trabajo empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Tarea> getTareas() {
        return tareas;
    }

    public Trabajo tareas(Set<Tarea> tareas) {
        this.tareas = tareas;
        return this;
    }

    public Trabajo addTarea(Tarea tarea) {
        this.tareas.add(tarea);
        tarea.getTrabajos().add(this);
        return this;
    }

    public Trabajo removeTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.getTrabajos().remove(this);
        return this;
    }

    public void setTareas(Set<Tarea> tareas) {
        this.tareas = tareas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trabajo trabajo = (Trabajo) o;
        if (trabajo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trabajo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trabajo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}