package com.duoc.services;

import com.duoc.dto.ComentarioDTO;
import com.duoc.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComentarioService {

    List<ComentarioDTO> getComentariosByIdPublicacion(Long idPublicacion);
    ComentarioDTO crearComentario(ComentarioDTO comentarioDTO);
    ComentarioDTO modificarComentario(ComentarioDTO comentarioDTO);
    void eliminarComentarioById(Long idComentario, Long idUsuario);
    void eliminarComentariosPorPublicacion(Long idPublicacion);
    void eliminarComentarioPorPublicacionYUsuario(Long idPublicacion, UsuarioDTO usuarioDTO);
}
