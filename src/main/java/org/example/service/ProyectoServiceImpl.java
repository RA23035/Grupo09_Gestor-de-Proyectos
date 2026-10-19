package org.example.service;

import org.example.dto.ProyectoDTO;
import org.example.model.Proyecto;
import org.example.repository.ProyectoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTO> listar() {
        return proyectoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> buscarPorId(Long id) {
        return proyectoRepository.findById(id).map(this::convertirADTO);
    }

    @Override
    public ProyectoDTO crear(ProyectoDTO proyectoDTO) {
        Proyecto proyecto = new Proyecto();
        aplicarDatos(proyecto, proyectoDTO);
        return convertirADTO(proyectoRepository.save(proyecto));
    }

    @Override
    public ProyectoDTO actualizar(Long id, ProyectoDTO proyectoDTO) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un proyecto con el id " + id));
        aplicarDatos(proyecto, proyectoDTO);
        return convertirADTO(proyectoRepository.save(proyecto));
    }

    @Override
    public void eliminar(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No existe un proyecto con el id " + id);
        }
        proyectoRepository.deleteById(id);
    }

    private ProyectoDTO convertirADTO(Proyecto proyecto) {
        return ProyectoDTO.builder()
                .id(proyecto.getId())
                .nombre(proyecto.getNombre())
                .descripcion(proyecto.getDescripcion())
                .fechaInicio(proyecto.getFechaInicio())
                .fechaFin(proyecto.getFechaFin())
                .estado(proyecto.getEstado())
                .build();
    }

    private void aplicarDatos(Proyecto proyecto, ProyectoDTO proyectoDTO) {
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setFechaInicio(proyectoDTO.getFechaInicio());
        proyecto.setFechaFin(proyectoDTO.getFechaFin());
        proyecto.setEstado(proyectoDTO.getEstado());
    }
}
