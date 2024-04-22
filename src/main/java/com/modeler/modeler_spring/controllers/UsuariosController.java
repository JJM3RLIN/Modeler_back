package com.modeler.modeler_spring.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.DTO.EmailDTO;
import com.modeler.modeler_spring.DTO.IdDTO;
import com.modeler.modeler_spring.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin(origins = "http://localhost:5173/", originPatterns = "*")
@RestController
@RequestMapping("modeler/api/usuarios")
public class UsuariosController {
    @Autowired
    UserService userService;
    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO){
       return ResponseEntity.ok().body(userService.create(userDTO));        
    }
    @PostMapping("/eliminar")
    public ResponseEntity<?> delete(@RequestBody IdDTO idDTO){      
        return ResponseEntity.ok().body(userService.delete(Integer.parseInt(idDTO.getId())));
    }
    @PostMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userService.update(userDTO));
    }
    @GetMapping("/verificar/{token}")
    public ResponseEntity<?> verificarToken(@PathVariable String token){
        return ResponseEntity.ok().body(userService.verificarToken(token));
    }
    @PostMapping("/buscar")
    public ResponseEntity<?> findByEmail(@RequestBody EmailDTO emailDTO){
        return ResponseEntity.ok().body(userService.findByEmail(emailDTO.getEmail()));
    }
    @GetMapping("/proyectos/{id}")
    public ResponseEntity<?> rutasUsuario(@PathVariable String id){
        return ResponseEntity.ok().body(userService.rutasParticipante(Integer.parseInt(id)));
    }
}