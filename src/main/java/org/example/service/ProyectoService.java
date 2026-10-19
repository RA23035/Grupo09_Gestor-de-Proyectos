package org.example.service;

import org.example.dto.ProyectoDTO;

import java.util.List;
import java.util.Optional;

public interface ProyectoService {

    List<ProyectoDTO> listar();

    Optional<ProyectoDTO> buscarPorId(Long id);

    ProyectoDTO crear(ProyectoDTO proyectoDTO);

    ProyectoDTO actualizar(Long id, ProyectoDTO proyectoDTO);

    void eliminar(Long id);
}
