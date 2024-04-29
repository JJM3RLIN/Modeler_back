package com.modeler.modeler_spring.services;
import java.util.List;
import java.util.Map;


import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.DTO.UserDTO;


public interface RutaService {
    public Map<String, String>  create(RutaDTO ruta);
    public Map<String, String> update(RutaDTO ruta);
    public Map<String, String> delete(String id);
    public Map<String, String> addUsuarioParticipante(String idRuta, String emailUsuario);
    public Map<String, String> removeUsuarioParticipante(String idRuta, String emailUsuario);
    public List<UserDTO> obtenerUsuarioParticipantesDeProyecto(String idRuta);
}
