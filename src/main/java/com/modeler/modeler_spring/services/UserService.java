package com.modeler.modeler_spring.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.User;

public interface UserService {
    public Optional<User> findByEmail(String email);
    public Map<String, String> create(UserDTO userDTO);
    public Map<String, String> update(UserDTO userDTO);
    public Map<String, String> delete(Integer id);
    public List<RutasUsuarioDTO>  rutasParticipante(Integer idUsuario);
}
