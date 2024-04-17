package com.modeler.modeler_spring.servicesImpl;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public static Map<String, String> response(String mensaje, String tipo){
        Map<String, String> response = new HashMap<>();
        response.put(tipo, mensaje);
        return response;
    }

}
