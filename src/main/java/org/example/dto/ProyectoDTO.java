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
 * Objeto de transferencia de datos para la entidad Proyecto.
 * Aísla al cliente de la entidad JPA y concentra las reglas de validación
 * de la entrada en la capa de REST.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoDTO {

    @Positive
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    @Size(max = 1000)
    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @NotBlank
    @Size(max = 30)
    private String estado;
}
