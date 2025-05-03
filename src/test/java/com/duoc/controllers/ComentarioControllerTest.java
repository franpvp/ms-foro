package com.duoc.controllers;

import com.duoc.dto.ComentarioDTO;
import com.duoc.dto.EliminarComentarioDTO;
import com.duoc.services.ComentarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ComentarioControllerTest {

    private ComentarioDTO comentarioDTO;

    @Mock
    private ComentarioService comentarioService;

    @InjectMocks
    private ComentarioController comentarioController;

    @BeforeEach
    void setuUp() {

        comentarioDTO = ComentarioDTO.builder()
                .idComentario(1L)
                .idPublicacion(1L)
                .idUsuario(1L)
                .contenido("CONTENIDO")
                .fechaCreacion(LocalDateTime.now())
                .build();

    }

    @Test
    void getComentariosByIdPublicacion() {

        ResponseEntity<List<ComentarioDTO>> resultado = comentarioController.getComentariosByIdPublicacion(1L);

        assertNotNull(resultado);

        verify(comentarioService).getComentariosByIdPublicacion(1L);

    }

    @Test
    void crearComentario() {

        ResponseEntity<ComentarioDTO> resultado = comentarioController.crearComentario(comentarioDTO);

        assertNotNull(resultado);

        verify(comentarioService).crearComentario(comentarioDTO);

    }

    @Test
    void modificarComentario() {

        ResponseEntity<ComentarioDTO> resultado = comentarioController.modificarComentario(comentarioDTO);

        assertNotNull(resultado);

        verify(comentarioService).modificarComentario(comentarioDTO);
    }

    @Test
    void eliminarComentarioById() {

        ResponseEntity<EliminarComentarioDTO> resultado = comentarioController.eliminarComentarioById(1L, 1L);

        assertNotNull(resultado);

        verify(comentarioService).eliminarComentarioById(1L, 1L);

    }

    @Test
    void getTodosLosComentarios() {

        ResponseEntity<List<ComentarioDTO>> resultado = comentarioController.getTodosLosComentarios();

        assertNotNull(resultado);

        verify(comentarioService).getTodosLosComentarios();


    }

    @Test
    void obtenerComentarioPorId() {

        ResponseEntity<ComentarioDTO> resultado = comentarioController.obtenerComentarioPorId(1L);

        assertNotNull(resultado);

        verify(comentarioService).obtenerComentarioPorId(1L);

    }
}