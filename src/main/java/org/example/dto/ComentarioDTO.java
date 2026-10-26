package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    private Long id;

    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    @Size(max = 2000, message = "El contenido no debe exceder 2000 caracteres")
    private String contenido;

    private LocalDateTime fechaCreacion;

    @NotNull(message = "El ID de la tarea es obligatorio")
    @Positive(message = "El ID de la tarea debe ser un número positivo")
    private Long tareaId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    private Long usuarioId;
}