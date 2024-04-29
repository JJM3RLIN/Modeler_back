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
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.services.RutaService;
@Service
public class RutaServiceImpl implements RutaService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RutaRepository rutaRepository;
    @Autowired
    EmailService emailService;

    @Override
    public Map<String, String> create(RutaDTO rutaDTO) {
        Map<String, String> response = new HashMap<String, String>();
        //Encontar al usuario creador
        Optional<User> usuarioCreador = userRepository.findById(rutaDTO.getUsuarioCreador());
        if(usuarioCreador.isPresent()){
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
            response.put("mensaje", "Proyecto creado de manera correcta");
        }
        else{
            response.put("error", "No se encontro el usuario creador");
        }
        
        return response;
    }

    @Override
    public Map<String, String> update(RutaDTO rutaDTO) {
       Optional<Ruta> ruta = rutaRepository.findById(rutaDTO.getId());
         if(ruta.isPresent()){
              ruta.get().setNombre(rutaDTO.getNombre());
              rutaRepository.save(ruta.get());
              return Response.response("Se actualizo el proyecto de manera correcta", "mensaje");
            }
        return Response.response(" Ocurrio un error al actualizar el proyecto", "error");
    }

    @Override
    public Map<String, String> delete(String id) {
        rutaRepository.deleteById(id);
        return Response.response("Se elimino el proyecto de manera correcta", "mensaje");
    }

    public Map<String, String> addUsuarioParticipante(String idRuta, String emailUsuario) {
       Map<String, String> response = new HashMap<String, String>();    
        Optional<Ruta> ruta = rutaRepository.findById(idRuta);
        //Verificar si el usuario existe en esa ruta y regresar mensaje si ya esta
        if(ruta.isPresent() && ruta.get().getUsuariosParticipantes().stream().anyMatch(usuario -> usuario.getEmail().equals(emailUsuario))){
            return Response.response("El usuario ya esta en la ruta", "error");
        }
        Optional<User> usuario = userRepository.findByEmail(emailUsuario);
        System.out.println("correo:"+usuario.get().getEmail());
        if(ruta.isPresent() && usuario.isPresent()){
            ruta.get().getUsuariosParticipantes().add(usuario.get());
            rutaRepository.save(ruta.get());
           // return Response.response("Usuario agregado de manera correcta","mensaje");
           response.put("mensaje", "Usuario agregado de manera correcta");
           response.put("nombre", usuario.get().getNombre());
           response.put("idUsuario", String.valueOf(usuario.get().getId()));
           response.put("email", usuario.get().getEmail());
           emailService.sendEmailAddProject(emailUsuario, "http://localhost:5173/login",usuario.get().getNombre());
           return response;
        }
        else{
           return Response.response("No se encontro la ruta o el usuario","error");
        }

    }
    public List<UserDTO> obtenerUsuarioParticipantesDeProyecto(String idRuta){
        List<UserDTO> usuariosParticipantes = rutaRepository.findUsuariosEnProyecto(idRuta);
        return usuariosParticipantes;
    }
    
    public Map<String, String> removeUsuarioParticipante(String idRuta, String emailUsuario) {
        Optional<Ruta> ruta = rutaRepository.findById(idRuta);
        //Verificar si el usuario existe en esa ruta y regresar mensaje si ya esta
        List<User> usersFiltrados = ruta.get().getUsuariosParticipantes().stream().filter(usuario -> !usuario.getEmail().equals(emailUsuario)) .collect(Collectors.toList());
        ruta.get().setUsuariosParticipantes(usersFiltrados);
        rutaRepository.save(ruta.get());
        return Response.response("mensaje", "Usuario eliminado de manera correcta");
    }

    
    
}
