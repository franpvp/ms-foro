package com.duoc.repositories;

import com.duoc.dto.ComentarioDTO;
import com.duoc.model.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {

    // Buscar comentarios por ID de publicación
    List<ComentarioEntity> findByIdPublicacion(Long idPublicacion);

}
