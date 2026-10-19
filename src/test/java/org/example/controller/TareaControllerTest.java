package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.TareaDTO;
import org.example.service.TareaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TareaService tareaService;

    private TareaDTO tareaValida() {
        return TareaDTO.builder()
                .id(10L)
                .titulo("Configurar la base de datos")
                .descripcion("Tarea de prueba")
                .fechaLimite(LocalDate.of(2026, 3, 15))
                .prioridad("ALTA")
                .estado("PENDIENTE")
                .proyectoId(1L)
                .usuarioAsignadoId(2L)
                .build();
    }

    @Test
    void listarDevuelveLasTareas() throws Exception {
        when(tareaService.listar()).thenReturn(List.of(tareaValida()));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].proyectoId").value(1));
    }

    @Test
    void listarPorProyectoDevuelveLasTareasDelProyecto() throws Exception {
        when(tareaService.listarPorProyecto(1L)).thenReturn(List.of(tareaValida()));

        mockMvc.perform(get("/api/tareas/proyecto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Configurar la base de datos"));
    }

    @Test
    void crearTareaValidaResponde201() throws Exception {
        when(tareaService.crear(any(TareaDTO.class))).thenReturn(tareaValida());

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tareaValida())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void crearTareaSinProyectoResponde400() throws Exception {
        TareaDTO invalida = TareaDTO.builder()
                .titulo("Tarea sin proyecto")
                .fechaLimite(LocalDate.of(2026, 3, 15))
                .prioridad("ALTA")
                .estado("PENDIENTE")
                .build();

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearTareaSinPrioridadResponde400() throws Exception {
        TareaDTO invalida = TareaDTO.builder()
                .titulo("Tarea sin prioridad")
                .fechaLimite(LocalDate.of(2026, 3, 15))
                .estado("PENDIENTE")
                .proyectoId(1L)
                .build();

        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }
}
