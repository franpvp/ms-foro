package com.duoc.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ComentarioDTOTest {


    private ComentarioDTO comentarioDTO;

    @BeforeEach
    void setUp() {

        comentarioDTO = ComentarioDTO.builder()
                .idComentario(1L)
                .idPublicacion(1L)
                .idUsuario(1L)
                .contenido("CONTENIDO")
                .fechaCreacion(LocalDateTime.now())
                .build();

    }


    @Test
    void testComentarioDTO() {

        comentarioDTO.setIdComentario(1L);
        comentarioDTO.setIdPublicacion(1L);
        comentarioDTO.setIdUsuario(1L);
        comentarioDTO.setContenido("CONTENIDO");

        assertEquals(1L, comentarioDTO.getIdComentario());
        assertEquals(1L, comentarioDTO.getIdPublicacion());
        assertEquals(1L, comentarioDTO.getIdUsuario());
        assertEquals("CONTENIDO", comentarioDTO.getContenido());
        assertNotNull(comentarioDTO.toString());

    }
}