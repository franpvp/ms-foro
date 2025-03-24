package com.duoc.services;

import com.duoc.adapter.controller.UsuarioClient;
import com.duoc.dto.PublicacionDTO;
import com.duoc.exceptions.IllegalNumberException;
import com.duoc.exceptions.PublicacionNotFoundException;
import com.duoc.exceptions.UnauthorizedException;
import com.duoc.exceptions.UsuarioNotFoundException;
import com.duoc.mapper.PublicacionMapper;
import com.duoc.model.PublicacionEntity;
import com.duoc.repositories.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService{

    private final PublicacionRepository publicacionRepository;
    private final PublicacionMapper publicacionMapper;
    private final UsuarioClient usuarioClient;
    private final ComentarioService comentarioService;

    @Override
    public List<PublicacionDTO> getPublicaciones() {
        List<PublicacionEntity> publicaciones = publicacionRepository.findAll();

        if(publicaciones.isEmpty()) {
            throw new PublicacionNotFoundException("No hay publicaciones registradas");
        }

        return publicaciones.stream()
                .map(publicacionMapper::publicacionEntityToDto)
                .toList();
    }

    @Override
    public PublicacionDTO getPublicacionById(Long idPublicacion) {

        if (idPublicacion <= 0) {
            throw new IllegalNumberException("Los ID no pueden ser negativos o cero.");
        }

        PublicacionEntity publicacionEntity = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new PublicacionNotFoundException("Publicación no encontrada"));
        return publicacionMapper.publicacionEntityToDto(publicacionEntity);

    }

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacion) {
        // Llamada al microservicio de usuarios para validar si existe
        ResponseEntity<Boolean> response = usuarioClient.usuarioExiste(publicacion.getIdUsuario());

        if (response.getBody() == null || !response.getBody()) {
            throw new UsuarioNotFoundException("El usuario con ID " + publicacion.getIdUsuario() + " no existe.");
        }

        publicacion.setFechaCreacion(LocalDateTime.now());

        // Transformar de Entity a DTO
        PublicacionEntity publicacionEntity = publicacionMapper.publicacionDtoToEntity(publicacion);
        PublicacionEntity publicacionGuardada = publicacionRepository.save(publicacionEntity);

        return publicacionMapper.publicacionEntityToDto(publicacionGuardada);
    }

    @Override
    public PublicacionDTO modificarPublicacion(PublicacionDTO publicacionDTO) {

        ResponseEntity<Boolean> response = usuarioClient.usuarioExiste(publicacionDTO.getIdUsuario());

        if (response.getBody() == null || !response.getBody()) {
            throw new UsuarioNotFoundException("El usuario con ID " + publicacionDTO.getIdUsuario() + " no existe.");
        }

        // Buscar la publicación en la base de datos
        PublicacionEntity publicacion = publicacionRepository.findByIdPublicacionAndIdUsuario(publicacionDTO.getIdPublicacion(), publicacionDTO.getIdUsuario())
                .orElseThrow(() -> new PublicacionNotFoundException("No se encontró la publicación con ID " + publicacionDTO.getIdPublicacion()));

        // Actualizar los valores de la publicación
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setFechaCreacion(LocalDateTime.now());

        // Guardar la publicación modificada
        PublicacionEntity publicacionActualizada = publicacionRepository.save(publicacion);

        // Convertir a DTO
        return publicacionMapper.publicacionEntityToDto(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacionById(Long idPublicacion, Long idUsuario) {
        // Validar si el usuario existe en el microservicio de usuarios (CREAR METODO PARA VALIDAR USUARIO)
        ResponseEntity<Boolean> response = usuarioClient.usuarioExiste(idUsuario);

        // Si es Falsom el usuario no existe
        if (response.getBody() == null || !response.getBody() || Boolean.FALSE.equals(response)) {
            throw new UsuarioNotFoundException("El usuario con ID " + idUsuario + " no existe.");
        }

        // Si el usuario existe, proceder con la eliminación de la publicación
        PublicacionEntity publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new PublicacionNotFoundException("Publicación no encontrada"));

        // Eliminar todos los comentarios antes de eliminar una publicación
        comentarioService.eliminarComentariosPorPublicacion(idPublicacion);
        // Eliminar publicación
        publicacionRepository.delete(publicacion);
    }


}
