package com.duoc.controllers;

import com.duoc.dto.ComentarioDTO;
import com.duoc.services.ComentarioService;
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
    public ResponseEntity<List<ComentarioDTO>> getComentariosByIdPublicacion(@PathVariable("id-publicacion") Long idPublicacion) {
        List<ComentarioDTO> comentarios = comentarioService.getComentariosByIdPublicacion(idPublicacion);

        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<ComentarioDTO> crearComentario(@RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO comentarioCreado = comentarioService.crearComentario(comentarioDTO);

        return new ResponseEntity<>(comentarioCreado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ComentarioDTO> modificarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        return null;
    }

    @DeleteMapping("/{id-comentario}")
    public void eliminarComentarioById(@PathVariable("id-comentario") Long idComentario) {

    }

    @DeleteMapping("/{id-publicacion}")
    public void eliminarComentariosPorPublicacion(@PathVariable("id-comentario") Long idPublicacion) {
    }


}
