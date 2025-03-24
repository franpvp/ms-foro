package com.duoc.services;

import com.duoc.adapter.controller.AuthClient;
import com.duoc.adapter.controller.UsuarioClient;
import com.duoc.dto.PublicacionDTO;
import com.duoc.dto.UsuarioDTO;
import com.duoc.enums.UserRole;
import com.duoc.exceptions.IllegalNumberException;
import com.duoc.exceptions.PublicacionNotFoundException;
import com.duoc.exceptions.UsuarioNoAutenticadoException;
import com.duoc.exceptions.UsuarioNotFoundException;
import com.duoc.mapper.PublicacionMapper;
import com.duoc.model.PublicacionEntity;
import com.duoc.repositories.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final PublicacionMapper publicacionMapper;
    private final UsuarioClient usuarioClient;
    private final ComentarioService comentarioService;
    private final AuthClient authClient;

    @Override
    public List<PublicacionDTO> getPublicaciones() {
        List<PublicacionEntity> publicaciones = publicacionRepository.findAll();

        if (publicaciones.isEmpty()) {
            throw new PublicacionNotFoundException("No hay publicaciones registradas en la base de datos.");
        }

        return publicaciones.stream()
                .map(publicacionMapper::publicacionEntityToDto)
                .toList();
    }

    @Override
    public PublicacionDTO getPublicacionById(Long idPublicacion) {
        if (idPublicacion == null || idPublicacion <= 0) {
            throw new IllegalNumberException(
                    String.format("El ID de la publicación no puede ser nulo, negativo o cero. Valor recibido: %d", idPublicacion));
        }

        PublicacionEntity publicacionEntity = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new PublicacionNotFoundException(
                        String.format("No se encontró ninguna publicación con el ID: %d", idPublicacion)));

        return publicacionMapper.publicacionEntityToDto(publicacionEntity);
    }

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        //Método para validar si el usuario está autenticado
        validarUsuarioLogeado(publicacionDTO.getIdUsuario());
        ResponseEntity<UsuarioDTO> response = usuarioClient.obtenerUsuario(publicacionDTO.getIdUsuario());

        if (response.getBody() == null) {
            throw new UsuarioNotFoundException(
                    String.format("No se encontró un usuario con el ID: %d. No se puede crear la publicación.", publicacionDTO.getIdUsuario()));
        }

        publicacionDTO.setFechaCreacion(LocalDateTime.now());

        PublicacionEntity publicacionEntity = publicacionMapper.publicacionDtoToEntity(publicacionDTO);
        PublicacionEntity publicacionGuardada = publicacionRepository.save(publicacionEntity);

        return publicacionMapper.publicacionEntityToDto(publicacionGuardada);
    }

    @Override
    public PublicacionDTO modificarPublicacion(PublicacionDTO publicacionDTO) {

        validarUsuarioLogeado(publicacionDTO.getIdUsuario());

        UsuarioDTO usuarioDTO = Optional.ofNullable(usuarioClient.obtenerUsuario(publicacionDTO.getIdUsuario()).getBody())
                .orElseThrow(() -> new UsuarioNotFoundException(
                        String.format("El usuario con ID %d no existe. No se puede modificar la publicación.", publicacionDTO.getIdUsuario())));

        PublicacionEntity publicacion;

        if (UserRole.ADMIN.equals(usuarioDTO.getRole()) || UserRole.MODERATOR.equals(usuarioDTO.getRole())) {
            publicacion = publicacionRepository.findById(publicacionDTO.getIdPublicacion())
                    .orElseThrow(() -> new PublicacionNotFoundException(
                            String.format("No se encontró la publicación con ID: %d", publicacionDTO.getIdPublicacion())));
        } else {
            publicacion = publicacionRepository.findByIdPublicacionAndIdUsuario(publicacionDTO.getIdPublicacion(), publicacionDTO.getIdUsuario())
                    .orElseThrow(() -> new PublicacionNotFoundException(
                            String.format("El usuario con ID %d no posee ninguna publicación con ID: %d",
                                    publicacionDTO.getIdUsuario(), publicacionDTO.getIdPublicacion())));
        }

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setContenido(publicacionDTO.getContenido());

        return publicacionMapper.publicacionEntityToDto(publicacionRepository.save(publicacion));
    }

    @Override
    public void eliminarPublicacionById(Long idPublicacion, Long idUsuario) {
        validarUsuarioLogeado(idUsuario);
        ResponseEntity<UsuarioDTO> response = usuarioClient.obtenerUsuario(idUsuario);

        if (response.getBody() == null) {
            throw new UsuarioNotFoundException(
                    String.format("El usuario con ID %d no existe. No se puede eliminar la publicación.", idUsuario));
        }

        PublicacionEntity publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new PublicacionNotFoundException(
                        String.format("No se encontró ninguna publicación con el ID: %d", idPublicacion)));

        UsuarioDTO usuarioDTO = response.getBody();

        if (UserRole.ADMIN.equals(usuarioDTO.getRole()) || UserRole.MODERATOR.equals(usuarioDTO.getRole())) {
            comentarioService.eliminarComentariosPorPublicacion(idPublicacion);
            publicacionRepository.delete(publicacion);
            log.info(String.format("Publicación con ID %d eliminada correctamente por usuario ADMIN/MODERATOR con ID %d",
                    idPublicacion, idUsuario));
        } else {
            comentarioService.eliminarComentarioPorPublicacionYUsuario(idPublicacion, usuarioDTO);
            PublicacionEntity publicacionEntity = publicacionRepository.findByIdPublicacionAndIdUsuario(idPublicacion, usuarioDTO.getId())
                    .orElseThrow(() -> new PublicacionNotFoundException(String.format("Intento fallido de eliminar publicación con ID %d. El usuario con ID %d no tiene permisos.",
                            idPublicacion, usuarioDTO.getId())));
            publicacionRepository.delete(publicacionEntity);
        }
    }

    /**
     * Método para validar si el usuario está logeado antes de realizar acciones.
     */
    private void validarUsuarioLogeado(Long idUsuario) {
        boolean isLoggedIn = Optional.ofNullable(authClient.verificarEstadoUsuario(idUsuario).getBody()).orElse(false);
        if (!isLoggedIn) {
            throw new UsuarioNoAutenticadoException(String.format("El usuario con ID %d no ha iniciado sesión.", idUsuario));
        }
    }
}