package com.modeler.modeler_spring.services;

public class Errors {
    
    // Error messages for userService
    public static final String USER_NOT_FOUND = "Usuario no encontrado";
    public static final String EMAIL_ALREADY_IN_USE = "El email ya esta en uso";
    public static final String EMAIL_NOT_REGISTERED = "El email no esta registrado";
    public static final String INVALID_TOKEN = "El token no es valido";

    // Error messages for rutaService
    public static final String RUTA_NOT_FOUND = "Ruta no encontrada";
    public static final String USER_ALREADY_IN_RUTA = "El usuario ya esta en la ruta";
    public static final String USER_NOT_IN_RUTA = "El usuario no esta en la ruta";
    public static final String CREATOR_USER_NOT_FOUND = "El usuario creador no fue encontrado";
    
    // Error messages for emailService
    public static final String EMAIL_NOT_SENT = "El email no pudo ser enviado";
}
