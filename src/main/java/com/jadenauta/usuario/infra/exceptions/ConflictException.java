package com.jadenauta.usuario.infra.exceptions;



public class ConflictException extends RuntimeException{

    public ConflictException(String mensage){
        super(mensage);
    }
    public ConflictException(String mensage, Throwable cause){
        super(mensage,cause);
    }



}
