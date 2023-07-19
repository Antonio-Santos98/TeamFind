package com.service.team.exceptions;

public class TeamExistsException extends RuntimeException{
    public TeamExistsException(){super("Team already exists!");}
}
