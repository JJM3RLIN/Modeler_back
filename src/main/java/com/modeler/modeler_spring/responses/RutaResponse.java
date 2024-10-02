package com.modeler.modeler_spring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RutaResponse {
    private String id;
    private String nombre;
    private String usuarioCreador;

    public RutaResponse(String id, String nombre, String usuarioCreador) {
        this.id = id;
        this.nombre = nombre;
        this.usuarioCreador = usuarioCreador;
    }
}
