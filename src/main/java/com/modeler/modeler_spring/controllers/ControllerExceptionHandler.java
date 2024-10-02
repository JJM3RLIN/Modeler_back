package com.modeler.modeler_spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.modeler.modeler_spring.configuration.ModelerException;

@ControllerAdvice
public class ControllerExceptionHandler {

    
    @ExceptionHandler(ModelerException.class)
    public ResponseEntity<Object> handleModelerException(ModelerException ex, WebRequest request) {
        // Retornar una respuesta con el mensaje de la excepción
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
