package org.example.service;

import org.example.dto.TareaDTO;

import java.util.List;
import java.util.Optional;

public interface TareaService {

    List<TareaDTO> listar();

    List<TareaDTO> listarPorProyecto(Long proyectoId);

    Optional<TareaDTO> buscarPorId(Long id);

    TareaDTO crear(TareaDTO tareaDTO);

    TareaDTO actualizar(Long id, TareaDTO tareaDTO);

    void eliminar(Long id);
}
