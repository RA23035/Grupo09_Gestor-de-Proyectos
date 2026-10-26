package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ComentarioDTO;
import org.example.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(comentarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.obtenerPorId(id));
    }

    @GetMapping("/tarea/{tareaId}")
    public ResponseEntity<List<ComentarioDTO>> obtenerPorTarea(@PathVariable Long tareaId) {
        return ResponseEntity.ok(comentarioService.obtenerPorTarea(tareaId));
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crear(@Valid @RequestBody ComentarioDTO dto) {
        ComentarioDTO creado = comentarioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        comentarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}