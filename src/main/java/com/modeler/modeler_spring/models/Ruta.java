package com.modeler.modeler_spring.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Ruta {
    @Id
    private String id; 
    private String nombre;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuarioCreador;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "ruta_usuario_participantes", 
    joinColumns = @JoinColumn(name = "ruta_id"), 
    inverseJoinColumns = @JoinColumn(name = "usuario_id"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"ruta_id", "user_id"})
    )
    private List<User> usuariosParticipantes;

    public Ruta() {
    }

    public Ruta(String id, String nombre, User usuarioCreador, List<User> usuariosParticipantes) {
        this.id = id;
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
        this.usuariosParticipantes = usuariosParticipantes;
    }
    public Ruta(String nombre, User usuarioCreador, List<User> usuariosParticipantes) {
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
        this.usuariosParticipantes = usuariosParticipantes;
    }
    
    public Ruta(String nombre, Date fecha, User usuarioCreador, List<User> usuariosParticipantes) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.usuarioCreador = usuarioCreador;
        this.usuariosParticipantes = usuariosParticipantes;
    }

    public Ruta(String id, String nombre, Date fecha, User usuarioCreador, List<User> usuariosParticipantes) {
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
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public User getUsuarioCreador() {
        return usuarioCreador;
    }
    public void setUsuarioCreador(User usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
    public List<User> getUsuariosParticipantes() {
        return usuariosParticipantes;
    }
    public void setUsuariosParticipantes(List<User> usuariosParticipantes) {
        this.usuariosParticipantes = usuariosParticipantes;
    }
    
}
