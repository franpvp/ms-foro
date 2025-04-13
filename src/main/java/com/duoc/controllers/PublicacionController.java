package com.duoc.controllers;

import com.duoc.dto.EliminarPublicacionDTO;
import com.duoc.dto.PublicacionDTO;
import com.duoc.services.PublicacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<PublicacionDTO> getPublicacionById(
            @NotNull @Valid @PathVariable("id-publicacion") Long idPublicacion
    ) {
        PublicacionDTO publicacionDTO = publicacionService.getPublicacionById(idPublicacion);
        return ResponseEntity.ok(publicacionDTO);
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crearPublicacion(
            @RequestBody PublicacionDTO publicacionDTO) {
        PublicacionDTO nuevaPublicacion = publicacionService.crearPublicacion(publicacionDTO);
        return ResponseEntity.ok(nuevaPublicacion);
    }

    @PutMapping
    public ResponseEntity<PublicacionDTO> modificarPublicacion(
            @NotNull @Valid @RequestBody PublicacionDTO publicacionDTO) {

        PublicacionDTO publicacionActualizada = publicacionService.modificarPublicacion(publicacionDTO);
        return ResponseEntity.ok(publicacionActualizada);
    }

    @DeleteMapping
    public ResponseEntity<EliminarPublicacionDTO> eliminarPublicacionById(
            @NotNull @Valid @RequestParam("id-publicacion") Long idPublicacion,
            @NotNull @Valid @RequestParam("id-usuario") Long idUsuario
    ) {
        publicacionService.eliminarPublicacionById(idPublicacion, idUsuario);
        EliminarPublicacionDTO eliminarPublicacionDTO = new EliminarPublicacionDTO("Publicacion eliminada con ID: " + idPublicacion);
        return ResponseEntity.ok(eliminarPublicacionDTO);
    }

}
