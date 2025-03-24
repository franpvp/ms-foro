package com.duoc.exceptions;

public class PublicacionNotFoundException extends RuntimeException{
    public PublicacionNotFoundException(String mensaje){
        super(mensaje);
    }
}
