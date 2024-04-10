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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("modeler/api/usuarios")
public class UsuariosController {
    @Autowired
    UserService userService;
    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO){
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", userService.create(userDTO));
       return ResponseEntity.ok().body(response);        
    }
    @PostMapping("/eliminar")
    public ResponseEntity<?> delete(@RequestBody IdDTO idDTO){      
        userService.delete(Integer.parseInt(idDTO.getId()));   
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario eliminado");
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userService.update(userDTO));
    }
    @PostMapping("/buscar")
    public ResponseEntity<?> findByEmail(@RequestBody EmailDTO emailDTO){
        return ResponseEntity.ok().body(userService.findByEmail(emailDTO.getEmail()));
    }
}