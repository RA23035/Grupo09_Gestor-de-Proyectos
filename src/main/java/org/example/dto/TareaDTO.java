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

/**
 * Objeto de transferencia de datos para la entidad Tarea.
 * Sustituye a las referencias directas a la entidad JPA en la capa de REST.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TareaDTO {

    @Positive
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String titulo;

    @Size(max = 1000)
    private String descripcion;

    @NotNull
    private LocalDate fechaLimite;

    @NotBlank
    @Size(max = 20)
    private String prioridad;

    @NotBlank
    @Size(max = 30)
    private String estado;

    @NotNull
    @Positive
    private Long proyectoId;

    @Positive
    private Long usuarioAsignadoId;
}
