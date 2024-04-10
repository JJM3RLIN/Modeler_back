package com.modeler.modeler_spring.services;

import java.util.Optional;

import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.User;

public interface UserService {
    public Optional<User> findByEmail(String email);
    public String create(UserDTO userDTO);
    public User update(UserDTO userDTO);
    public void delete(Integer id);
}
