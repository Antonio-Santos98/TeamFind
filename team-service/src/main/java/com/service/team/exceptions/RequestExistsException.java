package com.service.team.exceptions;

public class RequestExistsException extends RuntimeException{

    public RequestExistsException(){
        super("Request already exists for Team!");
    }
}
