package com.modeler.modeler_spring.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.UserService;

import jakarta.transaction.Transactional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public String create(UserDTO userDTO) {
        //Verificar si el email ya existe
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if(userOptional.isPresent()){
            return "Error:El email ya existe";
        }
        //Encriptar la contraseña
        String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
        //Inicialziar las rutas creadas como null
        List<Ruta> rutasCreadas = null;
        //Crear el usuario
        User user = new User(userDTO.getNombre(), userDTO.getEmail(), passwordEncoded, rutasCreadas);
        userRepository.save(user);
        return "Usuario creado";
    }

    @Override
    @Transactional
    public User update(UserDTO userDTO) {
        List<Ruta> rutas = new ObjectMapper().convertValue(userDTO.getRutasCreadas(), new TypeReference<List<Ruta>>(){});
        User user = new User(userDTO.getNombre(), userDTO.getEmail(), userDTO.getPassword(), rutas);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
    
}
