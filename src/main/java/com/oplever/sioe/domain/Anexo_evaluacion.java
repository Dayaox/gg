package com.oplever.sioe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Anexo_evaluacion.
 */
@Entity
@Table(name = "anexo_evaluacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anexo_evaluacion implements Serializable {

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

    @Column(name = "id_peticion")
    private String id_peticion;

    @ManyToOne
    private Evaluacion evaluacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public Anexo_evaluacion archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Anexo_evaluacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_peticion() {
        return id_peticion;
    }

    public Anexo_evaluacion id_peticion(String id_peticion) {
        this.id_peticion = id_peticion;
        return this;
    }

    public void setId_peticion(String id_peticion) {
        this.id_peticion = id_peticion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public Anexo_evaluacion evaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        return this;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Anexo_evaluacion anexo_evaluacion = (Anexo_evaluacion) o;
        if (anexo_evaluacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, anexo_evaluacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Anexo_evaluacion{" +
            "id=" + id +
            ", archivo='" + archivo + "'" +
            ", descripcion='" + descripcion + "'" +
            ", id_peticion='" + id_peticion + "'" +
            '}';
    }
}
