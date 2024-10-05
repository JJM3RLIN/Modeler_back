package com.modeler.modeler_spring.services;
import java.util.List;

import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.responses.UsuarioParticipanteResponse;
import com.modeler.modeler_spring.responses.RutaResponse;

public interface RutaService {
    public RutaResponse create(RutaDTO ruta) throws ModelerException;
    public String update(RutaDTO ruta) throws ModelerException;
    public String delete(String id) throws ModelerException;
    public UsuarioParticipanteResponse addUsuarioParticipante(String idRuta, String emailUsuario) throws ModelerException;
    public String removeUsuarioParticipante(String idRuta, String emailUsuario) throws ModelerException;
    public List<UserDTO> obtenerUsuariosParticipantesDeProyecto(String idRuta) throws ModelerException;
}
