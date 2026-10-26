package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ProyectoDTO;
import org.example.service.ProyectoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> obtenerTodos() {
        return ResponseEntity.ok(proyectoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoDTO> crear(@Valid @RequestBody ProyectoDTO dto) {
        ProyectoDTO creado = proyectoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProyectoDTO dto) {
        return ResponseEntity.ok(proyectoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}