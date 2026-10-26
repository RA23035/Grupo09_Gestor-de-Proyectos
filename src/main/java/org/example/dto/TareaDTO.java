package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {

    private Long id;

    @NotBlank(message = "El título de la tarea no puede estar vacío")
    @Size(max = 150, message = "El título no debe exceder 150 caracteres")
    private String titulo;

    @Size(max = 1000, message = "La descripción no debe exceder 1000 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha límite es obligatoria")
    private LocalDate fechaLimite;

    @NotBlank(message = "La prioridad no puede estar vacía")
    @Size(max = 20, message = "La prioridad no debe exceder 20 caracteres")
    private String prioridad;

    @NotBlank(message = "El estado de la tarea no puede estar vacío")
    @Size(max = 30, message = "El estado no debe exceder 30 caracteres")
    private String estado;

    @NotNull(message = "El ID del proyecto es obligatorio")
    @Positive(message = "El ID del proyecto debe ser un número positivo")
    private Long proyectoId;

    @Positive(message = "El ID del usuario asignado debe ser un número positivo")
    private Long usuarioAsignadoId;
}