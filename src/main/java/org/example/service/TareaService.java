package org.example.service;

import org.example.dto.TareaDTO;
import org.example.model.Proyecto;
import org.example.model.Tarea;
import org.example.model.Usuario;
import org.example.repository.ProyectoRepository;
import org.example.repository.TareaRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository,
                        ProyectoRepository proyectoRepository,
                        UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TareaDTO> obtenerTodas() {
        return tareaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public TareaDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        return convertirADTO(tarea);
    }

    public List<TareaDTO> obtenerPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public TareaDTO crear(TareaDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + dto.getProyectoId()));
        Usuario usuario = null;
        if (dto.getUsuarioAsignadoId() != null) {
            usuario = usuarioRepository.findById(dto.getUsuarioAsignadoId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioAsignadoId()));
        }
        Tarea tarea = Tarea.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaLimite(dto.getFechaLimite())
                .prioridad(dto.getPrioridad())
                .estado(dto.getEstado())
                .proyecto(proyecto)
                .usuarioAsignado(usuario)
                .build();
        Tarea guardada = tareaRepository.save(tarea);
        return convertirADTO(guardada);
    }

    public TareaDTO actualizar(Long id, TareaDTO dto) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        Proyecto proyecto = proyectoRepository.findById(dto.getProyectoId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + dto.getProyectoId()));
        Usuario usuario = null;
        if (dto.getUsuarioAsignadoId() != null) {
            usuario = usuarioRepository.findById(dto.getUsuarioAsignadoId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioAsignadoId()));
        }
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setFechaLimite(dto.getFechaLimite());
        tarea.setPrioridad(dto.getPrioridad());
        tarea.setEstado(dto.getEstado());
        tarea.setProyecto(proyecto);
        tarea.setUsuarioAsignado(usuario);
        Tarea actualizada = tareaRepository.save(tarea);
        return convertirADTO(actualizada);
    }

    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con ID: " + id);
        }
        tareaRepository.deleteById(id);
    }

    public TareaDTO convertirADTO(Tarea entidad) {
        return TareaDTO.builder()
                .id(entidad.getId())
                .titulo(entidad.getTitulo())
                .descripcion(entidad.getDescripcion())
                .fechaLimite(entidad.getFechaLimite())
                .prioridad(entidad.getPrioridad())
                .estado(entidad.getEstado())
                .proyectoId(entidad.getProyecto() != null ? entidad.getProyecto().getId() : null)
                .usuarioAsignadoId(entidad.getUsuarioAsignado() != null ? entidad.getUsuarioAsignado().getId() : null)
                .build();
    }
}