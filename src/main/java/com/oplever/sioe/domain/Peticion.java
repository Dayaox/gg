package com.oplever.sioe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Peticion.
 */
@Entity
@Table(name = "peticion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Peticion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "numero", length = 100, nullable = false)
    private String numero;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre_solicitante", length = 50, nullable = false)
    private String nombre_solicitante;

    @NotNull
    @Size(max = 50)
    @Column(name = "paterno_solicitante", length = 50, nullable = false)
    private String paterno_solicitante;

    @NotNull
    @Size(max = 50)
    @Column(name = "materno_solicitante", length = 50, nullable = false)
    private String materno_solicitante;

    @NotNull
    @Size(max = 100)
    @Column(name = "cargo_solicitante", length = 100, nullable = false)
    private String cargo_solicitante;

    @NotNull
    @Size(max = 500)
    @Column(name = "direccion_solicitante", length = 500, nullable = false)
    private String direccion_solicitante;

    @NotNull
    @Size(max = 2000)
    @Column(name = "acto_certificar", length = 2000, nullable = false)
    private String acto_certificar;

    @NotNull
    @Column(name = "oficio", nullable = false)
    private String oficio;

    @NotNull
    @Column(name = "nombre_responsable", nullable = false)
    private String nombre_responsable;

    @ManyToOne(optional = false)
    @NotNull
    private Usuario usuario;

    @ManyToOne(optional = false)
    @NotNull
    private Peticionario peticionario;

    @OneToMany(mappedBy = "peticion")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Anexo> anexos = new HashSet<>();

    @OneToOne(mappedBy = "peticion")
    @JsonIgnore
    private Prevencion prevencion;

    @OneToOne(mappedBy = "peticion")
    @JsonIgnore
    private Evaluacion evaluacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Peticion numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre_solicitante() {
        return nombre_solicitante;
    }

    public Peticion nombre_solicitante(String nombre_solicitante) {
        this.nombre_solicitante = nombre_solicitante;
        return this;
    }

    public void setNombre_solicitante(String nombre_solicitante) {
        this.nombre_solicitante = nombre_solicitante;
    }

    public String getPaterno_solicitante() {
        return paterno_solicitante;
    }

    public Peticion paterno_solicitante(String paterno_solicitante) {
        this.paterno_solicitante = paterno_solicitante;
        return this;
    }

    public void setPaterno_solicitante(String paterno_solicitante) {
        this.paterno_solicitante = paterno_solicitante;
    }

    public String getMaterno_solicitante() {
        return materno_solicitante;
    }

    public Peticion materno_solicitante(String materno_solicitante) {
        this.materno_solicitante = materno_solicitante;
        return this;
    }

    public void setMaterno_solicitante(String materno_solicitante) {
        this.materno_solicitante = materno_solicitante;
    }

    public String getCargo_solicitante() {
        return cargo_solicitante;
    }

    public Peticion cargo_solicitante(String cargo_solicitante) {
        this.cargo_solicitante = cargo_solicitante;
        return this;
    }

    public void setCargo_solicitante(String cargo_solicitante) {
        this.cargo_solicitante = cargo_solicitante;
    }

    public String getDireccion_solicitante() {
        return direccion_solicitante;
    }

    public Peticion direccion_solicitante(String direccion_solicitante) {
        this.direccion_solicitante = direccion_solicitante;
        return this;
    }

    public void setDireccion_solicitante(String direccion_solicitante) {
        this.direccion_solicitante = direccion_solicitante;
    }

    public String getActo_certificar() {
        return acto_certificar;
    }

    public Peticion acto_certificar(String acto_certificar) {
        this.acto_certificar = acto_certificar;
        return this;
    }

    public void setActo_certificar(String acto_certificar) {
        this.acto_certificar = acto_certificar;
    }

    public String getOficio() {
        return oficio;
    }

    public Peticion oficio(String oficio) {
        this.oficio = oficio;
        return this;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getNombre_responsable() {
        return nombre_responsable;
    }

    public Peticion nombre_responsable(String nombre_responsable) {
        this.nombre_responsable = nombre_responsable;
        return this;
    }

    public void setNombre_responsable(String nombre_responsable) {
        this.nombre_responsable = nombre_responsable;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Peticion usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Peticionario getPeticionario() {
        return peticionario;
    }

    public Peticion peticionario(Peticionario peticionario) {
        this.peticionario = peticionario;
        return this;
    }

    public void setPeticionario(Peticionario peticionario) {
        this.peticionario = peticionario;
    }

    public Set<Anexo> getAnexos() {
        return anexos;
    }

    public Peticion anexos(Set<Anexo> anexos) {
        this.anexos = anexos;
        return this;
    }

    public Peticion addAnexo(Anexo anexo) {
        this.anexos.add(anexo);
        anexo.setPeticion(this);
        return this;
    }

    public Peticion removeAnexo(Anexo anexo) {
        this.anexos.remove(anexo);
        anexo.setPeticion(null);
        return this;
    }

    public void setAnexos(Set<Anexo> anexos) {
        this.anexos = anexos;
    }

    public Prevencion getPrevencion() {
        return prevencion;
    }

    public Peticion prevencion(Prevencion prevencion) {
        this.prevencion = prevencion;
        return this;
    }

    public void setPrevencion(Prevencion prevencion) {
        this.prevencion = prevencion;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public Peticion evaluacion(Evaluacion evaluacion) {
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
        Peticion peticion = (Peticion) o;
        if (peticion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, peticion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Peticion{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", nombre_solicitante='" + nombre_solicitante + "'" +
            ", paterno_solicitante='" + paterno_solicitante + "'" +
            ", materno_solicitante='" + materno_solicitante + "'" +
            ", cargo_solicitante='" + cargo_solicitante + "'" +
            ", direccion_solicitante='" + direccion_solicitante + "'" +
            ", acto_certificar='" + acto_certificar + "'" +
            ", oficio='" + oficio + "'" +
            ", nombre_responsable='" + nombre_responsable + "'" +
            '}';
    }
}
