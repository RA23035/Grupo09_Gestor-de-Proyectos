package org.example.service;

import org.example.dto.ComentarioDTO;
import org.example.model.Comentario;
import org.example.model.Tarea;
import org.example.model.Usuario;
import org.example.repository.ComentarioRepository;
import org.example.repository.TareaRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             TareaRepository tareaRepository,
                             UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ComentarioDTO> obtenerTodos() {
        return comentarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ComentarioDTO> obtenerPorTarea(Long tareaId) {
        return comentarioRepository.findByTareaId(tareaId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ComentarioDTO crear(ComentarioDTO dto) {
        Tarea tarea = tareaRepository.findById(dto.getTareaId())
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + dto.getTareaId()));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
        Comentario comentario = Comentario.builder()
                .contenido(dto.getContenido())
                .tarea(tarea)
                .usuario(usuario)
                .build();
        Comentario guardado = comentarioRepository.save(comentario);
        return convertirADTO(guardado);
    }

    public void eliminar(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentario no encontrado con ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }

    public ComentarioDTO convertirADTO(Comentario entidad) {
        return ComentarioDTO.builder()
                .id(entidad.getId())
                .contenido(entidad.getContenido())
                .fechaCreacion(entidad.getFechaCreacion())
                .tareaId(entidad.getTarea() != null ? entidad.getTarea().getId() : null)
                .usuarioId(entidad.getUsuario() != null ? entidad.getUsuario().getId() : null)
                .build();
    }
}