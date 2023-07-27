package com.apigateway.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(){
        super("Incorrect password provided!");
    }
}
