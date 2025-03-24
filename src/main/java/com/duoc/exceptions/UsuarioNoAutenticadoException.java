package com.duoc.exceptions;

public class UsuarioNoAutenticadoException extends RuntimeException{
    public UsuarioNoAutenticadoException(String mensaje) {
        super(mensaje);
    }
}
