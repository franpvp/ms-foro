package com.duoc.dto;

import com.duoc.model.PublicacionEntity;
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
public class ComentarioDTO {

    @NotNull(message = "El campo idComentario no puede estar vacío")
    @Positive(message = "El campo idComentario debe ser un número positivo")
    private Long idComentario;

    @NotNull(message = "El campo publicacion no puede estar vacío")
    @Positive(message = "El campo idPublicacion debe ser un número positivo")
    private Long idPublicacion;

    @NotNull(message = "El campo idUsuario no puede estar vacío")
    @Positive(message = "El campo idUsuario debe ser un número positivo")
    private Long idUsuario;

    @NotNull(message = "El campo contenido no puede estar vacío")
    @Size(min = 1, max = 100, message = "El campo nombre debe tener entre 1 y 100 caracteres")
    private String contenido;

    @NotNull(message = "El campo fechaCreacion no puede estar vacío")
    private LocalDateTime fechaCreacion;

}
