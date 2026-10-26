package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.TareaDTO;
import org.example.service.TareaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTodas() {
        return ResponseEntity.ok(tareaService.obtenerTodas());
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<TareaDTO>> obtenerPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(tareaService.obtenerPorProyecto(proyectoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TareaDTO> crear(@Valid @RequestBody TareaDTO dto) {
        TareaDTO creada = tareaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TareaDTO dto) {
        return ResponseEntity.ok(tareaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}