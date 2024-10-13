package com.modeler.modeler_spring.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.services.Errors;
import com.modeler.modeler_spring.services.UserService;
import com.modeler.modeler_spring.configuration.ModelerException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByEmail(String email)  throws ModelerException{

        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            throw new ModelerException(Errors.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    @Transactional
    public String create(UserDTO userDTO) throws ModelerException {
        try {

            // Verificar si el email ya existe
            Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

            if (userOptional.isPresent()) {
                throw new ModelerException(Errors.EMAIL_ALREADY_IN_USE);
            }

            // Encriptar la contraseña
            String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());

            // Inicialziar las rutas creadas como null
            List<Ruta> rutasCreadas = null;

            // Crear un Token
            String token = UUID.randomUUID().toString();

            // Crear el usuario
            User user = new User(userDTO.getNombre(), userDTO.getEmail(), passwordEncoded, rutasCreadas, token);
            userRepository.save(user);

            // Enviar email de verificación
            emailService.sendEmailAccount(userDTO.getEmail(), "http://localhost:5173/verified/" + token,
                    userDTO.getNombre());
            logger.info("Nuevo usuario creado");
            return "Usuario creado, te hemos enviado un email para que verifiques tu cuenta";

        } catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String update(UserDTO userDTO) throws ModelerException {

        try {
            // Verificar que el usuario exista
            Optional<User> userOptional = userRepository.findById(userDTO.getId());
            if (!userOptional.isPresent()) {
                throw new ModelerException(Errors.USER_NOT_FOUND);
            }

            // Encriptar la contraseña
            String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());

            // Actualizar el usuario
            User user = userOptional.get();
            user.setNombre(userDTO.getNombre());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoded);
            userRepository.save(user);

            return "Usuario actualizado de manera correcta";
        } catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String delete(Integer id) throws ModelerException {

        try{
            //Verificar que el usuario exista
            Optional<User> userOptional = userRepository.findById(id);

            if(!userOptional.isPresent()) {
                throw new ModelerException(Errors.USER_NOT_FOUND);
            }

        userRepository.deleteById(id);
        return "Usuario eliminado de manera correcta";

        }catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public Boolean verifyToken(String token) throws ModelerException{
        try{
            Optional<User> userOptional = userRepository.findByToken(token);

            if (!userOptional.isPresent()) {
                throw new ModelerException(Errors.INVALID_TOKEN);
            }

            User user = userOptional.get();
            user.setToken(null);
            user.setVerificado(true);
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public List<RutasUsuarioDTO> rutasParticipante(Integer idUsuario) throws ModelerException {
        try{
            Optional<User> userOptional = userRepository.findById(idUsuario);

            if(!userOptional.isPresent()) {
                throw new ModelerException(Errors.USER_NOT_FOUND);
            }
            return rutaRepository.findByUsuariosParticipante(idUsuario);
        }
        catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public void sendEmailRecovery(String emailDestinatario) throws ModelerException{

        try {
            Optional<User> userOptional = userRepository.findByEmail(emailDestinatario);
            if(!userOptional.isPresent()) {
                throw new ModelerException(Errors.EMAIL_NOT_REGISTERED);
            }
            emailService.sendEmailRecovery(emailDestinatario, "http://localhost:5173/reset", userOptional.get().getNombre(), Integer.toString(userOptional.get().getId()));

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ModelerException(Errors.EMAIL_NOT_SENT);
        }
    }

    @Override
    public String recoverPassword(UserDTO userDTO) throws ModelerException{
        try {
            Optional<User> userOptional = userRepository.findById(userDTO.getId());
            if(!userOptional.isPresent()) {
                throw new ModelerException(Errors.USER_NOT_FOUND);
            }
            User user = userOptional.get();
            String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(passwordEncoded);
            userRepository.save(user);
            return "Contraseña actualizada de manera correcta";
        } catch (Exception e) {
            throw new ModelerException(e.getMessage());
        }
    }

}
