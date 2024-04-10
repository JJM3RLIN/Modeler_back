package com.modeler.modeler_spring.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.models.Ruta;

public interface RutaService {
    public Optional<Ruta> findByNombre(String nombre);
    public Map<String, String>  create(RutaDTO ruta);
    public Ruta update(RutaDTO ruta);
    public void delete(Integer id);
    public List<Ruta> findByUsuariosParticipantes(Integer idUsuario);
}
