package com.modeler.modeler_spring.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.modeler.modeler_spring.DTO.IdDTO;
import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.services.RutaService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("modeler/api/rutas")
public class RutasController {
    @Autowired
    RutaService rutaService;
    @PostMapping("/crear")
    public ResponseEntity<?> create (@RequestBody RutaDTO rutaDTO) {
        return ResponseEntity.ok().body(rutaService.create(rutaDTO));
    }
    @PostMapping("/pertenece")
    public ResponseEntity<?> update (@RequestBody IdDTO idDTO) {
        rutaService.findByUsuariosParticipantes(Integer.parseInt(idDTO.getId()));
        return null;
        //return ResponseEntity.ok().body(rutaService.update());
    }
    
}
