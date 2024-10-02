package com.modeler.modeler_spring.configuration;

public class ModelerException extends Exception {

    public ModelerException(String message) {
        super(message);
    }

    public ModelerException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
