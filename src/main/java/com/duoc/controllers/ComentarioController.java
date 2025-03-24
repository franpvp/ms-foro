package com.duoc.controllers;

import com.duoc.dto.ComentarioDTO;
import com.duoc.dto.EliminarComentarioDTO;
import com.duoc.services.ComentarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping("/{id-publicacion}")
    public ResponseEntity<List<ComentarioDTO>> getComentariosByIdPublicacion(
            @NotNull @Valid @PathVariable("id-publicacion") Long idPublicacion
    ) {
        List<ComentarioDTO> comentarios = comentarioService.getComentariosByIdPublicacion(idPublicacion);

        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crearComentario(
            @NotNull @Valid @RequestBody ComentarioDTO comentarioDTO
    ) {
        ComentarioDTO comentarioCreado = comentarioService.crearComentario(comentarioDTO);

        return new ResponseEntity<>(comentarioCreado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ComentarioDTO> modificarComentario(
            @NotNull @Valid @RequestBody ComentarioDTO comentarioDTO
    ) {
        ComentarioDTO comentarioModificado = comentarioService.modificarComentario(comentarioDTO);
        return new ResponseEntity<>(comentarioModificado, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<EliminarComentarioDTO> eliminarComentarioById(
            @NotNull @Valid @RequestParam("id-comentario") Long idComentario,
            @NotNull @Valid @RequestParam("id-usuario") Long idUsuario) {

        comentarioService.eliminarComentarioById(idComentario, idUsuario);
        EliminarComentarioDTO eliminarComentarioDTO = new EliminarComentarioDTO("Comentario eliminado exitosamente con ID: " + idComentario + " con ID usuario: " + idUsuario);

        return ResponseEntity.ok(eliminarComentarioDTO);
    }

}
