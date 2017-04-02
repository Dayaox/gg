package com.oplever.sioe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Anexo.
 */
@Entity
@Table(name = "anexo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anexo implements Serializable {

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

    @ManyToOne
    private Peticion peticion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivo() {
        return archivo;
    }

    public Anexo archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Anexo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Peticion getPeticion() {
        return peticion;
    }

    public Anexo peticion(Peticion peticion) {
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
        Anexo anexo = (Anexo) o;
        if (anexo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, anexo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Anexo{" +
            "id=" + id +
            ", archivo='" + archivo + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
