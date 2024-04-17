package com.modeler.modeler_spring.DTO;

import java.util.List;



public class RutaDTO {
    private String id;
    private String nombre;
    private String fecha;
    private Integer usuarioCreador;
    private List<UserDTO> usuariosParticipantes;
    public RutaDTO() {
    }
    public RutaDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public RutaDTO(String nombre, Integer usuarioCreador) {
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
    }
    public RutaDTO(String nombre, String fecha, Integer usuarioCreador, List<UserDTO> usuariosParticipantes) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.usuarioCreador = usuarioCreador;
        this.usuariosParticipantes = usuariosParticipantes;
    }
    public RutaDTO(String id, String nombre, String fecha, Integer usuarioCreador,
            List<UserDTO> usuariosParticipantes) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.usuarioCreador = usuarioCreador;
        this.usuariosParticipantes = usuariosParticipantes;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public Integer getUsuarioCreador() {
        return usuarioCreador;
    }
    public void setUsuarioCreador(Integer usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    public List<UserDTO> getUsuariosParticipantes() {
        return usuariosParticipantes;
    }
    public void setUsuariosParticipantes(List<UserDTO> usuariosParticipantes) {
        this.usuariosParticipantes = usuariosParticipantes;
    }
    
}
