package com.modeler.modeler_spring.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.RutaService;
@Service
public class RutaServiceImpl implements RutaService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RutaRepository rutaRepository;
    @Override
    public Optional<Ruta> findByNombre(String nombre) {
        throw new UnsupportedOperationException("Unimplemented method 'findByNombre'");
    }

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
            response.put("id", idRuta);
            response.put("nombre", rutaDTO.getNombre());
            response.put("usuarioCreador", usuarioCreador.get().getNombre());
            response.put("mensaje", "Proyecto creada de manera correcta");
        }
        else{
            response.put("error", "No se encontro el usuario creador");
        }
        
        return response;
    }

    @Override
    public Ruta update(RutaDTO ruta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    public List<Ruta> findByUsuariosParticipantes(Integer idUsuario) {
        return rutaRepository.findByUsuariosParticipantes(idUsuario);
    }
    
}
