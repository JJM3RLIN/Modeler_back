package com.modeler.modeler_spring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioParticipanteResponse {
    private String nombre;
    private String email;
    private String id;

    public UsuarioParticipanteResponse(String nombre, String email, String id) {
        this.nombre = nombre;
        this.email = email;
        this.id = id;
    }
}
