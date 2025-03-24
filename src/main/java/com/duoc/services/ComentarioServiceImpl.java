package com.duoc.services;

import com.duoc.adapter.controller.UsuarioClient;
import com.duoc.dto.ComentarioDTO;
import com.duoc.dto.UsuarioDTO;
import com.duoc.enums.UserRole;
import com.duoc.exceptions.ComentarioNotFoundException;
import com.duoc.exceptions.IllegalNumberException;
import com.duoc.exceptions.UsuarioNotFoundException;
import com.duoc.mapper.ComentarioMapper;
import com.duoc.model.ComentarioEntity;
import com.duoc.repositories.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService{

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;
    private final UsuarioClient usuarioClient;

    @Override
    public List<ComentarioDTO> getComentariosByIdPublicacion(Long idPublicacion) {
        List<ComentarioEntity> comentariosPub = comentarioRepository.findAllByIdPublicacion(idPublicacion)
                .orElseThrow(() -> new ComentarioNotFoundException("No hay comentarios en la publicación"));

        return comentariosPub.stream()
                .map(comentarioMapper::comentarioEntityToDto)
                .toList();
    }

    @Override
    public ComentarioDTO crearComentario(ComentarioDTO comentarioDTO) {

        if (comentarioDTO.getIdPublicacion() == null || comentarioDTO.getIdPublicacion() <= 0) {
            throw new IllegalNumberException("El ID de la publicación debe ser positivo y no nulo");
        }

        if (comentarioDTO.getIdUsuario() == null || comentarioDTO.getIdUsuario() <= 0) {
            throw new IllegalNumberException("El ID del usuario debe ser positivo y no nulo");
        }

        // Validar si el usuario del servicio externo existe
        ResponseEntity<UsuarioDTO> response = usuarioClient.obtenerUsuario(comentarioDTO.getIdUsuario());

        if (response == null || response.getStatusCode().isError() || response.getBody() == null) {
            throw new UsuarioNotFoundException("El usuario con ID " + comentarioDTO.getIdUsuario() + " no existe o el servicio no está disponible.");
        }

        ComentarioEntity comentarioEntity = comentarioMapper.comentarioDtoToEntity(comentarioDTO);
        ComentarioEntity comentarioCreado = comentarioRepository.save(comentarioEntity);

        return comentarioMapper.comentarioEntityToDto(comentarioCreado);
    }

    @Override
    public ComentarioDTO modificarComentario(ComentarioDTO comentarioDTO) {

        if (comentarioDTO.getIdPublicacion() <= 0 || comentarioDTO.getIdUsuario() <= 0) {
            throw new IllegalNumberException("El ID debe ser positivo y no nulo");
        }

        UsuarioDTO usuarioDTO = Optional.ofNullable(usuarioClient.obtenerUsuario(comentarioDTO.getIdUsuario()).getBody())
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario con ID " + comentarioDTO.getIdUsuario() + " no existe."));

        if (!comentarioRepository.existsById(comentarioDTO.getIdComentario())) {
            throw new ComentarioNotFoundException("El comentario con ID " + comentarioDTO.getIdComentario() + " no existe.");
        }

        ComentarioEntity comentario;

        if (UserRole.ADMIN.equals(usuarioDTO.getRole()) || UserRole.MODERATOR.equals(usuarioDTO.getRole())) {
            comentario = comentarioRepository.findById(comentarioDTO.getIdComentario())
                    .orElseThrow(() -> new ComentarioNotFoundException("Comentario no encontrado con ID: " + comentarioDTO.getIdComentario()));
        } else {
            comentario = comentarioRepository.findByIdComentarioAndIdUsuario(comentarioDTO.getIdComentario(), comentarioDTO.getIdUsuario())
                    .orElseThrow(() -> new ComentarioNotFoundException("Comentario no encontrado con ID: " + comentarioDTO.getIdComentario()));
        }

        comentario.setContenido(comentarioDTO.getContenido());

        return comentarioMapper.comentarioEntityToDto(comentarioRepository.save(comentario));
    }

    // Método para eliminar comentario por su id
    @Override
    public void eliminarComentarioById(Long idComentario, Long idUsuario) {

        ResponseEntity<UsuarioDTO> response = usuarioClient.obtenerUsuario(idUsuario);

        if (response.getBody() == null) {
            throw new UsuarioNotFoundException("El usuario con ID " + idUsuario + " no existe.");
        }

        if (!comentarioRepository.existsById(idComentario)) {
            throw new ComentarioNotFoundException("El comentario con ID " + idComentario + " no existe.");
        }
        UsuarioDTO usuarioDTO = response.getBody();

        if (UserRole.ADMIN.equals(usuarioDTO.getRole()) || UserRole.MODERATOR.equals(usuarioDTO.getRole())) {
            comentarioRepository.deleteById(idComentario);
        } else {
            ComentarioEntity comentarioEntity = comentarioRepository.findByIdComentarioAndIdUsuario(idComentario, idUsuario)
                    .orElseThrow(() -> new ComentarioNotFoundException(String.format("El comentario con id %s " +
                            "no pertenece al id usuario %s",idComentario,idUsuario)));
            comentarioRepository.delete(comentarioEntity);
        }
    }


    // Método para eliminar todos los comentarios de una publicación
    @Override
    public void eliminarComentariosPorPublicacion(Long idPublicacion) {

        if (idPublicacion <= 0) {
            throw new IllegalNumberException("El ID debe ser positivo y no nulo");
        }

        List<ComentarioEntity> comentarios = comentarioRepository.findAllByIdPublicacion(idPublicacion).orElseGet(List::of);

        if (!comentarios.isEmpty()) {
            comentarioRepository.deleteAll(comentarios);
        }
    }

    @Override
    public void eliminarComentarioPorPublicacionYUsuario(Long idPublicacion, UsuarioDTO usuarioDTO) {
        if (idPublicacion <= 0) {
            throw new IllegalNumberException("El ID debe ser positivo y no nulo");
        }

        List<ComentarioEntity> comentarios;

        if (UserRole.ADMIN.equals(usuarioDTO.getRole()) || UserRole.MODERATOR.equals(usuarioDTO.getRole())) {
            comentarios = comentarioRepository.findAllByIdPublicacion(idPublicacion).orElseGet(List::of);
        } else {
            comentarios = comentarioRepository.findAllByIdUsuario(usuarioDTO.getId()).orElseGet(List::of);
        }

        if (comentarios.isEmpty()) {
            log.info("No hay comentarios para la publicación {}", idPublicacion);
            return;
        }
        comentarioRepository.deleteAll(comentarios);
    }
}
