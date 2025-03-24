package com.duoc.services;

import com.duoc.dto.PublicacionDTO;
import com.duoc.model.PublicacionEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublicacionService {

    List<PublicacionDTO> getPublicaciones();
    PublicacionDTO crearPublicacion(PublicacionDTO publicacion);
    PublicacionDTO getPublicacionById(Long id);
    PublicacionDTO modificarPublicacion(PublicacionDTO publicacionDTO);
    void eliminarPublicacionById(Long id, Long idUsuario);

}
