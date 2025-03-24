package com.duoc.mapper;

import com.duoc.dto.ComentarioDTO;
import com.duoc.model.ComentarioEntity;
import org.springframework.stereotype.Component;

@Component
public class ComentarioMapper {

    // Entity a DTO
    public ComentarioEntity comentarioDtoToEntity(ComentarioDTO comentarioDTO) {
        return ComentarioEntity.builder()
                .idComentario(comentarioDTO.getIdComentario())
                .idPublicacion(comentarioDTO.getIdPublicacion())
                .idUsuario(comentarioDTO.getIdUsuario())
                .contenido(comentarioDTO.getContenido())
                .fechaCreacion(comentarioDTO.getFechaCreacion())
                .build();
    }

    // DTO a Entity
    public ComentarioDTO comentarioEntityToDto(ComentarioEntity comentarioEntity) {
        return ComentarioDTO.builder()
                .idComentario(comentarioEntity.getIdComentario())
                .idPublicacion(comentarioEntity.getIdPublicacion())
                .idUsuario(comentarioEntity.getIdUsuario())
                .contenido(comentarioEntity.getContenido())
                .fechaCreacion(comentarioEntity.getFechaCreacion())
                .build();
    }
}
