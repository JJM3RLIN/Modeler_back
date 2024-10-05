package com.modeler.modeler_spring.servicesImpl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Transactional(readOnly = true)
    @Override
    //Buscar que el usuario exista en la base de datos y despues llama al filtro para generar un token  
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(!userOptional.isPresent()) {
            session.setAttribute("error", "La cuenta con correo: " + username + " no existe");
            throw new UsernameNotFoundException("La cuenta con correo: " + username + " no existe");
        } 
        if(!userOptional.get().isVerificado()) {
            session.setAttribute("error", "No se ha verificado el correo");
            throw new UsernameNotFoundException("No se ha verificado el correo");
        }
        User user = userOptional.orElseThrow();
        session.setAttribute("nombre", user.getNombre());
        session.setAttribute("id", user.getId());
        session.setAttribute("verificado", user.isVerificado());
        session.setAttribute("error", null);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, Collections.emptyList());
    }
    
    
}
