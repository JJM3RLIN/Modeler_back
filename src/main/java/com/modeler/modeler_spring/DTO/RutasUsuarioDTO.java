package com.modeler.modeler_spring.DTO;
//clase para mapear las rutas de un usuario y evitar user Object
public class RutasUsuarioDTO {
    private String id;
    private String nombre;
    private Integer usuarioCreador;
    public RutasUsuarioDTO() {
    }
    public RutasUsuarioDTO(String id, String nombre, Integer usuarioCreador) {
        this.id =id;
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
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
    public Integer getUsuarioCreador() {
        return usuarioCreador;
    }
    public void setUsuarioCreador(Integer usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

}
