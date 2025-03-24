package com.duoc.services;

import com.duoc.dto.ComentarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComentarioService {

    List<ComentarioDTO> getComentariosByIdPublicacion(Long idPublicacion);
    ComentarioDTO crearComentario(ComentarioDTO comentarioDTO);
    ComentarioDTO modificarComentario(ComentarioDTO comentarioDTO);
    void eliminarComentarioById(Long idComentario);
    void eliminarComentariosPorPublicacion(Long idPublicacion);
}
