package org.example.controller;

import org.example.dto.TareaDTO;
import org.example.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<TareaDTO> listar() {
        return tareaService.listar();
    }

    @GetMapping("/proyecto/{proyectoId}")
    public List<TareaDTO> listarPorProyecto(@PathVariable Long proyectoId) {
        return tareaService.listarPorProyecto(proyectoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> buscarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TareaDTO> crear(@Valid @RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.crear(tareaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody TareaDTO tareaDTO) {
        return ResponseEntity.ok(tareaService.actualizar(id, tareaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
