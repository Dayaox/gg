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
 * A Peticionario.
 */
@Entity
@Table(name = "peticionario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Peticionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tipo", length = 100, nullable = false)
    private String tipo;

    @OneToMany(mappedBy = "peticionario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Peticion> peticions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public Peticionario tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Peticion> getPeticions() {
        return peticions;
    }

    public Peticionario peticions(Set<Peticion> peticions) {
        this.peticions = peticions;
        return this;
    }

    public Peticionario addPeticion(Peticion peticion) {
        this.peticions.add(peticion);
        peticion.setPeticionario(this);
        return this;
    }

    public Peticionario removePeticion(Peticion peticion) {
        this.peticions.remove(peticion);
        peticion.setPeticionario(null);
        return this;
    }

    public void setPeticions(Set<Peticion> peticions) {
        this.peticions = peticions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Peticionario peticionario = (Peticionario) o;
        if (peticionario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, peticionario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Peticionario{" +
            "id=" + id +
            ", tipo='" + tipo + "'" +
            '}';
    }
}
