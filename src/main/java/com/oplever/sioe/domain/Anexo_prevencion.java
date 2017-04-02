package com.oplever.sioe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Anexo_prevencion.
 */
@Entity
@Table(name = "anexo_prevencion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anexo_prevencion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "archivo", nullable = false)
    private String archivo;

    @NotNull
    @Size(max = 500)
    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @Column(name = "id_solicitud")
    private String id_solicitud;

    @ManyToOne
    private Prevencion prevencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public Anexo_prevencion archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Anexo_prevencion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_solicitud() {
        return id_solicitud;
    }

    public Anexo_prevencion id_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
        return this;
    }

    public void setId_solicitud(String id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Prevencion getPrevencion() {
        return prevencion;
    }

    public Anexo_prevencion prevencion(Prevencion prevencion) {
        this.prevencion = prevencion;
        return this;
    }

    public void setPrevencion(Prevencion prevencion) {
        this.prevencion = prevencion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anexo_prevencion anexo_prevencion = (Anexo_prevencion) o;
        if (anexo_prevencion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, anexo_prevencion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Anexo_prevencion{" +
            "id=" + id +
            ", archivo='" + archivo + "'" +
            ", descripcion='" + descripcion + "'" +
            ", id_solicitud='" + id_solicitud + "'" +
            '}';
    }
}
