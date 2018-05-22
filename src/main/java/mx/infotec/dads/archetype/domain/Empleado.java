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

    @OneToOne
    @JoinColumn(unique = true)
    private Direccion address;

    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trabajo> jobs = new HashSet<>();

    @ManyToOne
    private Departamento apartment;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "empleado_task",
               joinColumns = @JoinColumn(name="empleados_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tasks_id", referencedColumnName="id"))
    private Set<Tarea> tasks = new HashSet<>();

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

    public Direccion getAddress() {
        return address;
    }

    public Empleado address(Direccion direccion) {
        this.address = direccion;
        return this;
    }

    public void setAddress(Direccion direccion) {
        this.address = direccion;
    }

    public Set<Trabajo> getJobs() {
        return jobs;
    }

    public Empleado jobs(Set<Trabajo> trabajos) {
        this.jobs = trabajos;
        return this;
    }

    public Empleado addJob(Trabajo trabajo) {
        this.jobs.add(trabajo);
        trabajo.setEmpleado(this);
        return this;
    }

    public Empleado removeJob(Trabajo trabajo) {
        this.jobs.remove(trabajo);
        trabajo.setEmpleado(null);
        return this;
    }

    public void setJobs(Set<Trabajo> trabajos) {
        this.jobs = trabajos;
    }

    public Departamento getApartment() {
        return apartment;
    }

    public Empleado apartment(Departamento departamento) {
        this.apartment = departamento;
        return this;
    }

    public void setApartment(Departamento departamento) {
        this.apartment = departamento;
    }

    public Set<Tarea> getTasks() {
        return tasks;
    }

    public Empleado tasks(Set<Tarea> tareas) {
        this.tasks = tareas;
        return this;
    }

    public Empleado addTask(Tarea tarea) {
        this.tasks.add(tarea);
        tarea.getEmpleados().add(this);
        return this;
    }

    public Empleado removeTask(Tarea tarea) {
        this.tasks.remove(tarea);
        tarea.getEmpleados().remove(this);
        return this;
    }

    public void setTasks(Set<Tarea> tareas) {
        this.tasks = tareas;
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
