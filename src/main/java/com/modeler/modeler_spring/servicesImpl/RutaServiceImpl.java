package com.modeler.modeler_spring.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.responses.UsuarioParticipanteResponse;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.services.Errors;
import com.modeler.modeler_spring.services.RutaService;
import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.responses.RutaResponse;

@Service
public class RutaServiceImpl implements RutaService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RutaRepository rutaRepository;
    @Autowired
    EmailService emailService;

    @Override
    public RutaResponse create(RutaDTO rutaDTO)throws ModelerException{

        try{

            Map<String, String> response = new HashMap<String, String>();

            //Encontar al usuario creador
            Optional<User> usuarioCreador = userRepository.findById(rutaDTO.getUsuarioCreador());

            if(!usuarioCreador.isPresent()){
                throw new ModelerException(Errors.CREATOR_USER_NOT_FOUND);
            }
                //Generamos el string de id de la ruta
                String idRuta =  UUID.randomUUID().toString() + new Date().getTime();

                //Creamos la lista de usuarios participantes
                List<User> usuariosParticipantes = new ArrayList<User>();
                usuariosParticipantes.add(usuarioCreador.get());
                Ruta ruta = new Ruta(idRuta, rutaDTO.getNombre(), new Date(), usuarioCreador.get(), usuariosParticipantes);
                 rutaRepository.save(ruta);

                 //agregar la ruta al usuario creador
                usuarioCreador.get().getRutasCreadas().add(ruta);
                userRepository.save(usuarioCreador.get());
    
                response.put("id", idRuta);
                response.put("nombre", rutaDTO.getNombre());
                response.put("usuarioCreador", usuarioCreador.get().getNombre());
                return new RutaResponse(
                    idRuta, 
                    rutaDTO.getNombre(), 
                    usuarioCreador.get().getNombre());
            
        }
        catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
        
    }

    @Override
    public String update(RutaDTO rutaDTO) throws ModelerException {
        
        try{

            Optional<Ruta> ruta = rutaRepository.findById(rutaDTO.getId());

            if(!ruta.isPresent()){

                throw new ModelerException(Errors.RUTA_NOT_FOUND);
            }
            ruta.get().setNombre(rutaDTO.getNombre());
            rutaRepository.save(ruta.get());
            return "Se actualizo el proyecto de manera correcta";
        }
        catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public String delete(String id) throws ModelerException{
        try{
            if(!rutaRepository.findById(id).isPresent()){
                throw new ModelerException(Errors.RUTA_NOT_FOUND);
            }
            rutaRepository.deleteById(id);
            return "Se elimino el proyecto de manera correcta";

        }catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public UsuarioParticipanteResponse addUsuarioParticipante(String idRuta, String emailUsuario) throws ModelerException{
        try{
   
            Optional<Ruta> ruta = rutaRepository.findById(idRuta);
            Optional<User> usuario = userRepository.findByEmail(emailUsuario);

            if(!usuario.isPresent()){
                throw new ModelerException(Errors.USER_NOT_FOUND);
            }

            if(!ruta.isPresent()){
                throw new ModelerException(Errors.RUTA_NOT_FOUND);
            }

            if(ruta.get().getUsuariosParticipantes().stream().anyMatch(user -> user.getEmail().equals(emailUsuario))){
                throw new ModelerException(Errors.USER_ALREADY_IN_RUTA);
            }

            ruta.get().getUsuariosParticipantes().add(usuario.get());
            rutaRepository.save(ruta.get());
            emailService.sendEmailAddProject(emailUsuario, "http://localhost:5173/login",usuario.get().getNombre());

            return new UsuarioParticipanteResponse(
                usuario.get().getNombre(), 
                usuario.get().getEmail(), 
                String.valueOf(usuario.get().getId()));
        }
        catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
    }

    @Override
    public List<UserDTO> obtenerUsuariosParticipantesDeProyecto(String idRuta) throws ModelerException{
        try{
            rutaRepository.findById(idRuta).orElseThrow(() -> new ModelerException(Errors.RUTA_NOT_FOUND));
            List<UserDTO> usuariosParticipantes = rutaRepository.findUsuariosEnProyecto(idRuta);
            return usuariosParticipantes;
        }
        catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
    }
    
    @Override
    public String removeUsuarioParticipante(String idRuta, String emailUsuario) throws ModelerException{
        
        try{
            Optional<Ruta> ruta = rutaRepository.findById(idRuta);
            if(!ruta.isPresent()){
                throw new ModelerException(Errors.RUTA_NOT_FOUND);
            }

            userRepository.findByEmail(emailUsuario).orElseThrow(() -> new ModelerException(Errors.USER_NOT_FOUND));

            //Verificar si el usuario existe en esa ruta y regresar mensaje si ya esta
            if(!ruta.get().getUsuariosParticipantes().stream().anyMatch(usuario -> usuario.getEmail().equals(emailUsuario))){
                throw new ModelerException(Errors.USER_NOT_IN_RUTA);
            }
            List<User> usersFiltrados = ruta.get().getUsuariosParticipantes().stream().filter(usuario -> !usuario.getEmail().equals(emailUsuario)) .collect(Collectors.toList());
            ruta.get().setUsuariosParticipantes(usersFiltrados);
            rutaRepository.save(ruta.get());
            return "Usuario eliminado de manera correcta";
        }
        catch(Exception e){
            throw new ModelerException(e.getMessage());
        }
    }

    
    
}
