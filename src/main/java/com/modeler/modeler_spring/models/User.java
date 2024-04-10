package com.modeler.modeler_spring.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;   
    @JoinColumn(name = "id_usuario")
    @OneToMany(targetEntity = Ruta.class, cascade = CascadeType.ALL)
    private List<Ruta> rutasCreadas;

    public User() {
    }
    
    public User(String nombre, String email, String password, List<Ruta> rutasCreadas) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rutasCreadas = rutasCreadas;
    }
    
    public User(Integer id, String nombre, String email, String password, List<Ruta> rutasCreadas) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rutasCreadas = rutasCreadas;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Ruta> getRutasCreadas() {
        return rutasCreadas;
    }
    public void setRutasCreadas(List<Ruta> rutasCreadas) {
        this.rutasCreadas = rutasCreadas;
    }
    
}
