package com.modeler.modeler_spring.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String email;
    private String token;
    @Column(columnDefinition = "boolean default false")
    private boolean verificado;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;   
    @OneToMany(mappedBy = "usuarioCreador", cascade = CascadeType.ALL)
    private List<Ruta> rutasCreadas;

    public User() {
    }
    
    public User(String nombre, String email, String password, List<Ruta> rutasCreadas) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rutasCreadas = rutasCreadas;
    }
    public User(String nombre, String email, String password, List<Ruta> rutasCreadas, String token) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rutasCreadas = rutasCreadas;
        this.token = token;
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
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public List<Ruta> getRutasCreadas() {
        return rutasCreadas;
    }
    public void setRutasCreadas(List<Ruta> rutasCreadas) {
        this.rutasCreadas = rutasCreadas;
    }
    
}
