package mx.infotec.dads.archetype.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotNull
    @Min(value = 12)
    @Max(value = 23)
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotNull
    @Min(value = 13L)
    @Max(value = 23L)
    @Column(name = "numero_credencial", nullable = false)
    private Long numeroCredencial;

    @NotNull
    @DecimalMin(value = "15")
    @DecimalMax(value = "23")
    @Column(name = "sueldo", precision=10, scale=2, nullable = false)
    private BigDecimal sueldo;

    @NotNull
    @DecimalMin(value = "12")
    @DecimalMax(value = "23")
    @Column(name = "impuesto", nullable = false)
    private Float impuesto;

    @NotNull
    @DecimalMin(value = "11")
    @DecimalMax(value = "23")
    @Column(name = "impuesto_detalle", nullable = false)
    private Double impuestoDetalle;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @NotNull
    @Column(name = "fecha_local_date", nullable = false)
    private LocalDate fechaLocalDate;

    @NotNull
    @Column(name = "fecha_zone_date_time", nullable = false)
    private ZonedDateTime fechaZoneDateTime;

    @NotNull
    @Size(min = 5, max = 5000000)
    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @Column(name = "imagen_content_type", nullable = false)
    private String imagenContentType;

    @NotNull
    @Size(min = 78, max = 5000000)
    @Lob
    @Column(name = "imagen_any_blob", nullable = false)
    private byte[] imagenAnyBlob;

    @Column(name = "imagen_any_blob_content_type", nullable = false)
    private String imagenAnyBlobContentType;

    @NotNull
    @Size(min = 21, max = 5000000)
    @Lob
    @Column(name = "imagen_blob", nullable = false)
    private byte[] imagenBlob;

    @Column(name = "imagen_blob_content_type", nullable = false)
    private String imagenBlobContentType;

    @NotNull
    @Size(min = 3, max = 5000000)
    @Lob
    @Column(name = "jhi_desc", nullable = false)
    private String desc;

    @NotNull
    @Column(name = "instante", nullable = false)
    private Instant instante;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public Persona edad(Integer edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Long getNumeroCredencial() {
        return numeroCredencial;
    }

    public Persona numeroCredencial(Long numeroCredencial) {
        this.numeroCredencial = numeroCredencial;
        return this;
    }

    public void setNumeroCredencial(Long numeroCredencial) {
        this.numeroCredencial = numeroCredencial;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public Persona sueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Float getImpuesto() {
        return impuesto;
    }

    public Persona impuesto(Float impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Float impuesto) {
        this.impuesto = impuesto;
    }

    public Double getImpuestoDetalle() {
        return impuestoDetalle;
    }

    public Persona impuestoDetalle(Double impuestoDetalle) {
        this.impuestoDetalle = impuestoDetalle;
        return this;
    }

    public void setImpuestoDetalle(Double impuestoDetalle) {
        this.impuestoDetalle = impuestoDetalle;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Persona activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaLocalDate() {
        return fechaLocalDate;
    }

    public Persona fechaLocalDate(LocalDate fechaLocalDate) {
        this.fechaLocalDate = fechaLocalDate;
        return this;
    }

    public void setFechaLocalDate(LocalDate fechaLocalDate) {
        this.fechaLocalDate = fechaLocalDate;
    }

    public ZonedDateTime getFechaZoneDateTime() {
        return fechaZoneDateTime;
    }

    public Persona fechaZoneDateTime(ZonedDateTime fechaZoneDateTime) {
        this.fechaZoneDateTime = fechaZoneDateTime;
        return this;
    }

    public void setFechaZoneDateTime(ZonedDateTime fechaZoneDateTime) {
        this.fechaZoneDateTime = fechaZoneDateTime;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Persona imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Persona imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public byte[] getImagenAnyBlob() {
        return imagenAnyBlob;
    }

    public Persona imagenAnyBlob(byte[] imagenAnyBlob) {
        this.imagenAnyBlob = imagenAnyBlob;
        return this;
    }

    public void setImagenAnyBlob(byte[] imagenAnyBlob) {
        this.imagenAnyBlob = imagenAnyBlob;
    }

    public String getImagenAnyBlobContentType() {
        return imagenAnyBlobContentType;
    }

    public Persona imagenAnyBlobContentType(String imagenAnyBlobContentType) {
        this.imagenAnyBlobContentType = imagenAnyBlobContentType;
        return this;
    }

    public void setImagenAnyBlobContentType(String imagenAnyBlobContentType) {
        this.imagenAnyBlobContentType = imagenAnyBlobContentType;
    }

    public byte[] getImagenBlob() {
        return imagenBlob;
    }

    public Persona imagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
        return this;
    }

    public void setImagenBlob(byte[] imagenBlob) {
        this.imagenBlob = imagenBlob;
    }

    public String getImagenBlobContentType() {
        return imagenBlobContentType;
    }

    public Persona imagenBlobContentType(String imagenBlobContentType) {
        this.imagenBlobContentType = imagenBlobContentType;
        return this;
    }

    public void setImagenBlobContentType(String imagenBlobContentType) {
        this.imagenBlobContentType = imagenBlobContentType;
    }

    public String getDesc() {
        return desc;
    }

    public Persona desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getInstante() {
        return instante;
    }

    public Persona instante(Instant instante) {
        this.instante = instante;
        return this;
    }

    public void setInstante(Instant instante) {
        this.instante = instante;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Persona persona = (Persona) o;
        if (persona.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), persona.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", edad=" + getEdad() +
            ", numeroCredencial=" + getNumeroCredencial() +
            ", sueldo=" + getSueldo() +
            ", impuesto=" + getImpuesto() +
            ", impuestoDetalle=" + getImpuestoDetalle() +
            ", activo='" + isActivo() + "'" +
            ", fechaLocalDate='" + getFechaLocalDate() + "'" +
            ", fechaZoneDateTime='" + getFechaZoneDateTime() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", imagenAnyBlob='" + getImagenAnyBlob() + "'" +
            ", imagenAnyBlobContentType='" + getImagenAnyBlobContentType() + "'" +
            ", imagenBlob='" + getImagenBlob() + "'" +
            ", imagenBlobContentType='" + getImagenBlobContentType() + "'" +
            ", desc='" + getDesc() + "'" +
            ", instante='" + getInstante() + "'" +
            "}";
    }
}
