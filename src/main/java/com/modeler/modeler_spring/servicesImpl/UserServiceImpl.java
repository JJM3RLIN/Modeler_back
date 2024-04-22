package com.modeler.modeler_spring.servicesImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.services.UserService;

import jakarta.transaction.Transactional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Map<String, String> create(UserDTO userDTO) {
        //Verificar si el email ya existe
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if(userOptional.isPresent()){
            return Response.response("El email ya esta en uso","error");
        }
        //Encriptar la contraseña
        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        //Inicialziar las rutas creadas como null
        List<Ruta> rutasCreadas = null;
        //Crear un Token
        String token = UUID.randomUUID().toString();
        //Crear el usuario
        User user = new User(userDTO.getNombre(), userDTO.getEmail(), passwordEncoded,rutasCreadas, token);
        userRepository.save(user);
        //Enviar email de verificación
        emailService.sendEmailAccount(userDTO.getEmail(), "http://localhost:5173/verified/"+token, userDTO.getNombre());
        return Response.response("Usuario creado, te hemos enviado un email para que verifiques tu cuenta","mensaje");
    }

    @Override
    @Transactional
    public Map<String, String> update(UserDTO userDTO) {
        List<Ruta> rutas = new ObjectMapper().convertValue(userDTO.getRutasCreadas(), new TypeReference<List<Ruta>>(){});
        User user = new User(userDTO.getNombre(), userDTO.getEmail(), userDTO.getPassword(), rutas);
         userRepository.save(user);
         return Response.response("Usuario actualizado de manera correcta", "mensaje");
    }

    @Override
    @Transactional
    public Map<String, String> delete(Integer id) {
        //Verificar que el usuario exista
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return Response.response("El usuario no existe", "error");
        }
        userRepository.deleteById(id);
        return Response.response("Usuario eliminado de manera correcta", "mensaje");
    }

    @Override
    public Map<String, String> verificarToken(String token) {
        Optional<User> userOptional = userRepository.findByToken(token);
        if(!userOptional.isPresent()){
            return Response.response("false", "validacion");
        }
        User user = userOptional.get();
        user.setToken(null);
        user.setVerificado(true);
        userRepository.save(user);
        return Response.response("true", "validacion");
    }
    @Override
    public List<RutasUsuarioDTO> rutasParticipante(Integer idUsuario) {
        return rutaRepository.findByUsuariosParticipante(idUsuario);
    }
    
}
