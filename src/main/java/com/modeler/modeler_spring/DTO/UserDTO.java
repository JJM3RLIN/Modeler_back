package com.modeler.modeler_spring.DTO;

import java.util.List;

public class UserDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private List<RutaDTO> rutasCreadas;
    
    public UserDTO() {
    }
    public UserDTO(Integer id) {
        this.id = id;
    }
    public UserDTO(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String nombre, String email, String password, List<RutaDTO> rutasCreadas) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rutasCreadas = rutasCreadas;
    }
    
    public UserDTO(Integer id, String nombre, String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public UserDTO(Integer id, String nombre, String email, String password, List<RutaDTO> rutasCreadas) {
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
    public List<RutaDTO> getRutasCreadas() {
        return rutasCreadas;
    }
    public void setRutasCreadas(List<RutaDTO> rutasCreadas) {
        this.rutasCreadas = rutasCreadas;
    }
    
}
