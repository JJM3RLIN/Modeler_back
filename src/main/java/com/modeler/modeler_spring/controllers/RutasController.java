package com.modeler.modeler_spring.controllers;

import org.springframework.web.bind.annotation.RestController;


import com.modeler.modeler_spring.DTO.AddUserDTO;
import com.modeler.modeler_spring.DTO.IdDTO;
import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.services.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.modeler.modeler_spring.configuration.ModelerException;


@CrossOrigin(origins = "http://localhost:5173/", originPatterns = "*")
@RestController
@RequestMapping("modeler/api/rutas")
public class RutasController {
    @Autowired
    RutaService rutaService;

    @PostMapping("/crear")
    public ResponseEntity<?> create (@RequestBody RutaDTO rutaDTO) throws ModelerException {
        return ResponseEntity.ok().body(rutaService.create(rutaDTO));
    }
    @PostMapping("/actualizar")
    public ResponseEntity<?> update (@RequestBody RutaDTO rutaDTO) throws ModelerException {
        return ResponseEntity.ok().body(rutaService.update(rutaDTO));
    }
    @PostMapping("/eliminar")
    public ResponseEntity<?> delete (@RequestBody IdDTO idDTO) throws ModelerException {
        return ResponseEntity.ok().body(rutaService.delete(idDTO.getId()));
    }
    @PostMapping("/usuarios-participan")
    public ResponseEntity<?> usuariosParticipanEnProyecto(@RequestBody IdDTO idDTO) throws ModelerException {
        return ResponseEntity.ok().body(rutaService.obtenerUsuariosParticipantesDeProyecto(idDTO.getId()));
    }
    
    @PostMapping("user/agregar-colaborador")
    public ResponseEntity<?> addUser (@RequestBody AddUserDTO addUserDTO) throws ModelerException {
        return ResponseEntity.ok().body(rutaService.addUsuarioParticipante(addUserDTO.getIdRuta(), addUserDTO.getEmailUsuario()));
    }
    @PostMapping("user/eliminar")
    public ResponseEntity<?> removeUser (@RequestBody AddUserDTO addUserDTO) throws ModelerException {
        rutaService.removeUsuarioParticipante(addUserDTO.getIdRuta(), addUserDTO.getEmailUsuario());
        return ResponseEntity.ok().build();
    }
    
}
