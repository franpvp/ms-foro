package com.duoc.controllers.advice;

import com.duoc.dto.ErrorResponse;
import com.duoc.exceptions.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PublicacionControllerAdvice {

    @ExceptionHandler(PublicacionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarPublicacionNoEncontrado(PublicacionNotFoundException ex){
        log.error("Publicación no encontrada: {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(IllegalNumberException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarIllegalNumberException(IllegalNumberException ex){
        log.error("No se deben ingresar número negativos : {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ComentarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarPublicacionNoEncontrado(ComentarioNotFoundException ex){
        log.error("Comentario no encontrado: {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarUsuarioNoEncontrado(UsuarioNotFoundException ex){
        log.error("Usuario no encontrado: {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarErrorServicioExterno(FeignException ex){
        log.error("Error en servicio externo: {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UsuarioNoAutenticadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse manejarUsuarioNoAutenticado(UsuarioNoAutenticadoException ex){
        log.error("El usuario no está autenticado : {}", ex.getMessage());

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
