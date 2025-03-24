package com.duoc.services;

import com.duoc.dto.ComentarioDTO;
import com.duoc.dto.PublicacionDTO;
import com.duoc.exceptions.ComentarioNotFoundException;
import com.duoc.exceptions.IllegalNumberException;
import com.duoc.exceptions.UsuarioNotFoundException;
import com.duoc.mapper.ComentarioMapper;
import com.duoc.model.ComentarioEntity;
import com.duoc.repositories.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService{

    private final ComentarioRepository comentarioRepository;
    private final ComentarioMapper comentarioMapper;

    @Override
    public List<ComentarioDTO> getComentariosByIdPublicacion(Long idPublicacion) {
        List<ComentarioEntity> comentariosPub = comentarioRepository.findByIdPublicacion(idPublicacion);

        if(comentariosPub.isEmpty()) {
            throw new ComentarioNotFoundException("No hay comentarios en la publicación");
        }

        return comentariosPub.stream()
                .map(comentarioMapper::comentarioEntityToDto)
                .toList();
    }

    @Override
    public ComentarioDTO crearComentario(ComentarioDTO comentarioDTO) {

        if (comentarioDTO.getIdPublicacion() <= 0 && comentarioDTO.getIdUsuario() <= 0) {
            throw new IllegalNumberException("El ID debe ser positivo y no nulo");
        }

        ComentarioEntity comentarioEntity = comentarioMapper.comentarioDtoToEntity(comentarioDTO);
        ComentarioEntity comentarioCreado = comentarioRepository.save(comentarioEntity);

        return comentarioMapper.comentarioEntityToDto(comentarioCreado);
    }

    @Override
    public ComentarioDTO modificarComentario(ComentarioDTO comentarioDTO) {

        if (comentarioDTO.getIdPublicacion() <= 0 && comentarioDTO.getIdUsuario() <= 0) {
            throw new IllegalNumberException("El ID debe ser positivo y no nulo");
        }

        return comentarioRepository.findById(comentarioDTO.getIdComentario())
                .map(comentarioEntity -> {
                    comentarioEntity.setContenido(comentarioDTO.getContenido());

                    ComentarioEntity comentarioEntityModificado = comentarioRepository.save(comentarioEntity);
                    return comentarioMapper.comentarioEntityToDto(comentarioEntityModificado);
                })
                .orElseThrow(() -> new ComentarioNotFoundException("Comentario no encontrado con ID: " + comentarioDTO.getContenido()));
    }

    // Método para eliminar comentario por su id
    @Override
    public void eliminarComentarioById(Long idComentario) {
        if (!comentarioRepository.existsById(idComentario)) {
            throw new ComentarioNotFoundException("El comentario con ID " + idComentario + " no existe.");
        }
        comentarioRepository.deleteById(idComentario);
    }


    // Método para eliminar todos los comentarios de una publicación
    @Override
    public void eliminarComentariosPorPublicacion(Long idPublicacion) {
        List<ComentarioEntity> comentarios = comentarioRepository.findByIdPublicacion(idPublicacion);

        if (!comentarios.isEmpty()) {
            comentarioRepository.deleteAll(comentarios);
        }
    }
}
