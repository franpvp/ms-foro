package com.duoc.exceptions;

public class ComentarioNotFoundException extends RuntimeException{
    public ComentarioNotFoundException(String mensaje) {
        super(mensaje);
    }
}
