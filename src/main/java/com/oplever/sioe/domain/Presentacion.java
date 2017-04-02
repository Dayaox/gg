package com.oplever.sioe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Presentacion.
 */
@Entity
@Table(name = "presentacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Presentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acuerdo")
    private String acuerdo;

    @Column(name = "notificacion")
    private String notificacion;

    @Column(name = "status_completado")
    private Boolean status_completado;

    @OneToOne
    @JoinColumn(unique = true)
    private Evaluacion evaluacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcuerdo() {
        return acuerdo;
    }

    public Presentacion acuerdo(String acuerdo) {
        this.acuerdo = acuerdo;
        return this;
    }

    public void setAcuerdo(String acuerdo) {
        this.acuerdo = acuerdo;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public Presentacion notificacion(String notificacion) {
        this.notificacion = notificacion;
        return this;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    public Boolean isStatus_completado() {
        return status_completado;
    }

    public Presentacion status_completado(Boolean status_completado) {
        this.status_completado = status_completado;
        return this;
    }

    public void setStatus_completado(Boolean status_completado) {
        this.status_completado = status_completado;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public Presentacion evaluacion(Evaluacion evaluacion) {
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
        Presentacion presentacion = (Presentacion) o;
        if (presentacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, presentacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Presentacion{" +
            "id=" + id +
            ", acuerdo='" + acuerdo + "'" +
            ", notificacion='" + notificacion + "'" +
            ", status_completado='" + status_completado + "'" +
            '}';
    }
}
