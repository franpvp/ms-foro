package com.duoc.repositories;

import com.duoc.dto.ComentarioDTO;
import com.duoc.model.ComentarioEntity;
import com.duoc.model.PublicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

    // Buscar comentarios por ID de publicación
    Optional<List<ComentarioEntity>> findAllByIdPublicacion(Long idPublicacion);
    Optional<ComentarioEntity> findByIdComentarioAndIdUsuario(Long idComentario, Long idUsuario);

    Optional<List<ComentarioEntity>> findAllByIdUsuario(Long idUsuario);

}
