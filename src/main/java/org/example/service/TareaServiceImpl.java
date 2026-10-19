package org.example.service;

import org.example.dto.TareaDTO;
import org.example.model.Proyecto;
import org.example.model.Tarea;
import org.example.model.Usuario;
import org.example.repository.ProyectoRepository;
import org.example.repository.TareaRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaServiceImpl(TareaRepository tareaRepository,
                             ProyectoRepository proyectoRepository,
                             UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaDTO> listar() {
        return tareaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaDTO> listarPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId).stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TareaDTO> buscarPorId(Long id) {
        return tareaRepository.findById(id).map(this::convertirADTO);
    }

    @Override
    public TareaDTO crear(TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        aplicarDatos(tarea, tareaDTO);
        return convertirADTO(tareaRepository.save(tarea));
    }

    @Override
    public TareaDTO actualizar(Long id, TareaDTO tareaDTO) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe una tarea con el id " + id));
        aplicarDatos(tarea, tareaDTO);
        return convertirADTO(tareaRepository.save(tarea));
    }

    @Override
    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No existe una tarea con el id " + id);
        }
        tareaRepository.deleteById(id);
    }

    private TareaDTO convertirADTO(Tarea tarea) {
        return TareaDTO.builder()
                .id(tarea.getId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .fechaLimite(tarea.getFechaLimite())
                .prioridad(tarea.getPrioridad())
                .estado(tarea.getEstado())
                .proyectoId(tarea.getProyecto() != null ? tarea.getProyecto().getId() : null)
                .usuarioAsignadoId(tarea.getUsuarioAsignado() != null ? tarea.getUsuarioAsignado().getId() : null)
                .build();
    }

    private void aplicarDatos(Tarea tarea, TareaDTO tareaDTO) {
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setFechaLimite(tareaDTO.getFechaLimite());
        tarea.setPrioridad(tareaDTO.getPrioridad());
        tarea.setEstado(tareaDTO.getEstado());
        tarea.setProyecto(obtenerProyecto(tareaDTO.getProyectoId()));
        tarea.setUsuarioAsignado(obtenerUsuarioAsignado(tareaDTO.getUsuarioAsignadoId()));
    }

    private Proyecto obtenerProyecto(Long proyectoId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No existe un proyecto con el id " + proyectoId));
        return proyecto;
    }

    private Usuario obtenerUsuarioAsignado(Long usuarioAsignadoId) {
        if (usuarioAsignadoId == null) {
            return null;
        }
        return usuarioRepository.findById(usuarioAsignadoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "No existe un usuario con el id " + usuarioAsignadoId));
    }
}
