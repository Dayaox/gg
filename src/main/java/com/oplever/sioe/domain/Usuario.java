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
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password")
    private String password;

    @Column(name = "zona")
    private String zona;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "privilegio")
    private Integer privilegio;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Peticion> peticions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public Usuario password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZona() {
        return zona;
    }

    public Usuario zona(String zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDistrito() {
        return distrito;
    }

    public Usuario distrito(String distrito) {
        this.distrito = distrito;
        return this;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Usuario municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getPrivilegio() {
        return privilegio;
    }

    public Usuario privilegio(Integer privilegio) {
        this.privilegio = privilegio;
        return this;
    }

    public void setPrivilegio(Integer privilegio) {
        this.privilegio = privilegio;
    }

    public Set<Peticion> getPeticions() {
        return peticions;
    }

    public Usuario peticions(Set<Peticion> peticions) {
        this.peticions = peticions;
        return this;
    }

    public Usuario addPeticion(Peticion peticion) {
        this.peticions.add(peticion);
        peticion.setUsuario(this);
        return this;
    }

    public Usuario removePeticion(Peticion peticion) {
        this.peticions.remove(peticion);
        peticion.setUsuario(null);
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
        Usuario usuario = (Usuario) o;
        if (usuario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", password='" + password + "'" +
            ", zona='" + zona + "'" +
            ", distrito='" + distrito + "'" +
            ", municipio='" + municipio + "'" +
            ", privilegio='" + privilegio + "'" +
            '}';
    }
}
