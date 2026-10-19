package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ProyectoDTO;
import org.example.service.ProyectoService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProyectoController.class)
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProyectoService proyectoService;

    private ProyectoDTO proyectoValido() {
        return ProyectoDTO.builder()
                .id(1L)
                .nombre("Sistema de Gestion de Proyectos")
                .descripcion("Proyecto de prueba")
                .fechaInicio(LocalDate.of(2026, 2, 2))
                .fechaFin(LocalDate.of(2026, 12, 30))
                .estado("ACTIVO")
                .build();
    }

    @Test
    void listarDevuelveLosProyectos() throws Exception {
        when(proyectoService.listar()).thenReturn(List.of(proyectoValido()));

        mockMvc.perform(get("/api/proyectos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Sistema de Gestion de Proyectos"));
    }

    @Test
    void buscarProyectoInexistenteResponde404() throws Exception {
        when(proyectoService.buscarPorId(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/proyectos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearProyectoValidoResponde201() throws Exception {
        when(proyectoService.crear(any(ProyectoDTO.class))).thenReturn(proyectoValido());

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proyectoValido())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    void crearProyectoSinNombreResponde400() throws Exception {
        ProyectoDTO invalido = ProyectoDTO.builder()
                .descripcion("Proyecto sin nombre")
                .fechaInicio(LocalDate.of(2026, 2, 2))
                .fechaFin(LocalDate.of(2026, 12, 30))
                .estado("ACTIVO")
                .build();

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearProyectoSinFechasResponde400() throws Exception {
        ProyectoDTO invalido = ProyectoDTO.builder()
                .nombre("Proyecto sin fechas")
                .estado("ACTIVO")
                .build();

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarProyectoValidoResponde200() throws Exception {
        when(proyectoService.actualizar(eq(1L), any(ProyectoDTO.class))).thenReturn(proyectoValido());

        mockMvc.perform(put("/api/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proyectoValido())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void eliminarProyectoResponde204() throws Exception {
        mockMvc.perform(delete("/api/proyectos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarProyectoInexistenteResponde404() throws Exception {
        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "No existe un proyecto con el id 99"))
                .when(proyectoService).eliminar(99L);

        mockMvc.perform(delete("/api/proyectos/99"))
                .andExpect(status().isNotFound());
    }
}
