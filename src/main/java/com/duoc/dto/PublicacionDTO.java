package com.duoc.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionDTO {

    @NotNull(message = "El campo idPublicacion no puede estar vacío")
    @Positive(message = "El campo idPublicacion debe ser un número positivo")
    private Long idPublicacion;

    @NotNull(message = "El campo idUsuario no puede estar vacío")
    @Positive(message = "El campo idUsuario debe ser un número positivo")
    private Long idUsuario;

    @NotNull(message = "El campo titulo no puede estar vacío")
    @Size(min = 1, max = 50, message = "El campo titulo debe tener entre 1 y 50 caracteres")
    private String titulo;

    @NotNull(message = "El campo categoria no puede estar vacío")
    @Size(min = 2, max = 50, message = "El campo categoria debe tener entre 1 y 50 caracteres")
    private String categoria;

    @NotNull(message = "El campo contenido no puede estar vacío")
    @Size(min = 1, max = 100, message = "El campo contenido debe tener entre 1 y 100 caracteres")
    private String contenido;

    @NotNull(message = "El campo fechaCreacion no puede estar vacío")
    private LocalDateTime fechaCreacion;

}
