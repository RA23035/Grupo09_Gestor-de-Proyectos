package org.example.service;

import org.example.dto.ProyectoDTO;
import org.example.model.Proyecto;
import org.example.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<ProyectoDTO> obtenerTodos() {
        return proyectoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ProyectoDTO obtenerPorId(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        return convertirADTO(proyecto);
    }

    public ProyectoDTO crear(ProyectoDTO dto) {
        Proyecto proyecto = convertirAEntidad(dto);
        Proyecto guardado = proyectoRepository.save(proyecto);
        return convertirADTO(guardado);
    }

    public ProyectoDTO actualizar(Long id, ProyectoDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        proyecto.setEstado(dto.getEstado());
        Proyecto actualizado = proyectoRepository.save(proyecto);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con ID: " + id);
        }
        proyectoRepository.deleteById(id);
    }

    public ProyectoDTO convertirADTO(Proyecto entidad) {
        return ProyectoDTO.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .descripcion(entidad.getDescripcion())
                .fechaInicio(entidad.getFechaInicio())
                .fechaFin(entidad.getFechaFin())
                .estado(entidad.getEstado())
                .build();
    }

    public Proyecto convertirAEntidad(ProyectoDTO dto) {
        return Proyecto.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .estado(dto.getEstado())
                .build();
    }
}