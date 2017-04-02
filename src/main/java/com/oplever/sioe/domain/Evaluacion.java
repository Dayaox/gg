package com.oplever.sioe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Evaluacion.
 */
@Entity
@Table(name = "evaluacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status_evaluacion")
    private Integer status_evaluacion;

    @OneToMany(mappedBy = "evaluacion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo_evaluacion> anexo_evaluacions = new HashSet<>();

    @OneToOne(mappedBy = "n")
    @JsonIgnore
    private Procedente procedente;

    @OneToOne(mappedBy = "evaluacion")
    @JsonIgnore
    private Improcedente improcedente;

    @OneToOne(mappedBy = "evaluacion")
    @JsonIgnore
    private Presentacion presentacion;

    @OneToOne
    @JoinColumn(unique = true)
    private Peticion peticion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus_evaluacion() {
        return status_evaluacion;
    }

    public Evaluacion status_evaluacion(Integer status_evaluacion) {
        this.status_evaluacion = status_evaluacion;
        return this;
    }

    public void setStatus_evaluacion(Integer status_evaluacion) {
        this.status_evaluacion = status_evaluacion;
    }

    public Set<Anexo_evaluacion> getAnexo_evaluacions() {
        return anexo_evaluacions;
    }

    public Evaluacion anexo_evaluacions(Set<Anexo_evaluacion> anexo_evaluacions) {
        this.anexo_evaluacions = anexo_evaluacions;
        return this;
    }

    public Evaluacion addAnexo_evaluacion(Anexo_evaluacion anexo_evaluacion) {
        this.anexo_evaluacions.add(anexo_evaluacion);
        anexo_evaluacion.setEvaluacion(this);
        return this;
    }

    public Evaluacion removeAnexo_evaluacion(Anexo_evaluacion anexo_evaluacion) {
        this.anexo_evaluacions.remove(anexo_evaluacion);
        anexo_evaluacion.setEvaluacion(null);
        return this;
    }

    public void setAnexo_evaluacions(Set<Anexo_evaluacion> anexo_evaluacions) {
        this.anexo_evaluacions = anexo_evaluacions;
    }

    public Procedente getProcedente() {
        return procedente;
    }

    public Evaluacion procedente(Procedente procedente) {
        this.procedente = procedente;
        return this;
    }

    public void setProcedente(Procedente procedente) {
        this.procedente = procedente;
    }

    public Improcedente getImprocedente() {
        return improcedente;
    }

    public Evaluacion improcedente(Improcedente improcedente) {
        this.improcedente = improcedente;
        return this;
    }

    public void setImprocedente(Improcedente improcedente) {
        this.improcedente = improcedente;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public Evaluacion presentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
        return this;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Peticion getPeticion() {
        return peticion;
    }

    public Evaluacion peticion(Peticion peticion) {
        this.peticion = peticion;
        return this;
    }

    public void setPeticion(Peticion peticion) {
        this.peticion = peticion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evaluacion evaluacion = (Evaluacion) o;
        if (evaluacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evaluacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evaluacion{" +
            "id=" + id +
            ", status_evaluacion='" + status_evaluacion + "'" +
            '}';
    }
}
