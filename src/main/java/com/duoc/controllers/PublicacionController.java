package com.duoc.controllers;

import com.duoc.dto.PublicacionDTO;
import com.duoc.services.PublicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publicaciones")
public class PublicacionController {

    private final PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> getPublicaciones() {
        List<PublicacionDTO> publicacionDTOS = publicacionService.getPublicaciones();

        return ResponseEntity.ok(publicacionDTOS);
    }

    @GetMapping("/{id-publicacion}")
    public ResponseEntity<PublicacionDTO> getPublicacionById(@PathVariable("id-publicacion") Long idPublicacion) {
        PublicacionDTO publicacionDTO = publicacionService.getPublicacionById(idPublicacion);
        return ResponseEntity.ok(publicacionDTO);
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crearPublicacion(
            @Valid @RequestBody PublicacionDTO publicacionDTO) {
        PublicacionDTO nuevaPublicacion = publicacionService.crearPublicacion(publicacionDTO);
        return ResponseEntity.ok(nuevaPublicacion);
    }

    @PutMapping
    public ResponseEntity<PublicacionDTO> modificarPublicacion(
            @Valid @RequestBody PublicacionDTO publicacionDTO) {

        PublicacionDTO publicacionActualizada = publicacionService.modificarPublicacion(publicacionDTO);
        return ResponseEntity.ok(publicacionActualizada);
    }

    @DeleteMapping("/{id-publicacion}/{id-usuario}")
    public ResponseEntity<Void> eliminarPublicacionById(
            @PathVariable("id-publicacion") Long idPublicacion,
            @PathVariable("id-usuario") Long idUsuario
    ) {
        publicacionService.eliminarPublicacionById(idPublicacion, idUsuario);
        return ResponseEntity.noContent().build();
    }

}
