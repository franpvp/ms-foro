package com.duoc.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String mensaje) {
        super(mensaje);
    }
}
