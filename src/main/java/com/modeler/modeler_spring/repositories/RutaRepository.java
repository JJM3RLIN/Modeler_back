package com.modeler.modeler_spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.models.Ruta;
import java.util.Optional;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, String>{
    Optional<Ruta> findByNombre(String nombre);
    
    @Query("SELECT new com.modeler.modeler_spring.DTO.RutasUsuarioDTO(r.id, r.nombre, r.usuarioCreador.id) FROM Ruta r JOIN r.usuariosParticipantes u WHERE u.id = ?1")
    List<RutasUsuarioDTO> findByUsuariosParticipante(Integer id);//Proyectos en los que participa el usuario

    @Query("SELECT new com.modeler.modeler_spring.DTO.UserDTO(u.id, u.nombre, u.email) FROM Ruta r JOIN r.usuariosParticipantes u WHERE r.id = ?1")
    List<UserDTO> findUsuariosEnProyecto(String id);//traer los usuarios que participan a partir de la ruta en la que se encuentran
    
}
