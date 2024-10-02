package com.modeler.modeler_spring.services;

import java.util.List;
import java.util.Optional;

import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.configuration.ModelerException;

public interface UserService {
    public Optional<User> findByEmail(String email) throws ModelerException;
    public String create(UserDTO userDTO) throws ModelerException;
    public String update(UserDTO userDTO) throws ModelerException;
    public String delete(Integer id) throws ModelerException;
    public Boolean verifyToken(String token) throws ModelerException;
    public List<RutasUsuarioDTO>  rutasParticipante(Integer idUsuario) throws ModelerException;
    public void sendEmailRecovery(String emailDestinatario) throws ModelerException;
    public String recoverPassword(UserDTO userDTO) throws ModelerException;
}
