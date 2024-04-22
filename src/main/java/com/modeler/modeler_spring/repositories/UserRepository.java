package com.modeler.modeler_spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.modeler_spring.models.User;



public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    
} 