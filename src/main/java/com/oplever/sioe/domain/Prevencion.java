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
 * A Prevencion.
 */
@Entity
@Table(name = "prevencion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prevencion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oficio")
    private String oficio;

    @Column(name = "notificacion")
    private String notificacion;

    @Column(name = "respuesta")
    private String respuesta;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "prevencion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo_prevencion> anexo_prevencions = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Peticion peticion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOficio() {
        return oficio;
    }

    public Prevencion oficio(String oficio) {
        this.oficio = oficio;
        return this;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public Prevencion notificacion(String notificacion) {
        this.notificacion = notificacion;
        return this;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public Prevencion respuesta(String respuesta) {
        this.respuesta = respuesta;
        return this;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Boolean isStatus() {
        return status;
    }

    public Prevencion status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Anexo_prevencion> getAnexo_prevencions() {
        return anexo_prevencions;
    }

    public Prevencion anexo_prevencions(Set<Anexo_prevencion> anexo_prevencions) {
        this.anexo_prevencions = anexo_prevencions;
        return this;
    }

    public Prevencion addAnexo_prevencion(Anexo_prevencion anexo_prevencion) {
        this.anexo_prevencions.add(anexo_prevencion);
        anexo_prevencion.setPrevencion(this);
        return this;
    }

    public Prevencion removeAnexo_prevencion(Anexo_prevencion anexo_prevencion) {
        this.anexo_prevencions.remove(anexo_prevencion);
        anexo_prevencion.setPrevencion(null);
        return this;
    }

    public void setAnexo_prevencions(Set<Anexo_prevencion> anexo_prevencions) {
        this.anexo_prevencions = anexo_prevencions;
    }

    public Peticion getPeticion() {
        return peticion;
    }

    public Prevencion peticion(Peticion peticion) {
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
        Prevencion prevencion = (Prevencion) o;
        if (prevencion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, prevencion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Prevencion{" +
            "id=" + id +
            ", oficio='" + oficio + "'" +
            ", notificacion='" + notificacion + "'" +
            ", respuesta='" + respuesta + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
