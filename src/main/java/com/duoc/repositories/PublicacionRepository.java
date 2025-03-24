package com.duoc.repositories;

import com.duoc.model.PublicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<PublicacionEntity, Long> {
    Optional<PublicacionEntity> findByIdPublicacionAndIdUsuario(Long idPublicacion, Long idUsuario);
}
