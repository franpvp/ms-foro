package com.duoc.mapper;

import com.duoc.dto.PublicacionDTO;
import com.duoc.model.PublicacionEntity;
import org.springframework.stereotype.Component;

@Component
public class PublicacionMapper {

    // Entity a DTO
    public PublicacionEntity publicacionDtoToEntity(PublicacionDTO publicacionDTO) {
        return PublicacionEntity.builder()
                .idPublicacion(publicacionDTO.getIdPublicacion())
                .idUsuario(publicacionDTO.getIdUsuario())
                .titulo(publicacionDTO.getTitulo())
                .categoria(publicacionDTO.getCategoria())
                .contenido(publicacionDTO.getContenido())
                .fechaCreacion(publicacionDTO.getFechaCreacion())
                .build();
    }

    // DTO a Entity
    public PublicacionDTO publicacionEntityToDto(PublicacionEntity publicacionEntity) {
        return PublicacionDTO.builder()
                .idPublicacion(publicacionEntity.getIdPublicacion())
                .idUsuario(publicacionEntity.getIdUsuario())
                .titulo(publicacionEntity.getTitulo())
                .categoria(publicacionEntity.getCategoria())
                .contenido(publicacionEntity.getContenido())
                .fechaCreacion(publicacionEntity.getFechaCreacion())
                .build();
    }
}
