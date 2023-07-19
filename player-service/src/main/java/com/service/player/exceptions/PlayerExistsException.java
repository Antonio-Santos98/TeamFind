package com.service.player.exceptions;

public class PlayerExistsException extends RuntimeException{
    public PlayerExistsException(){
        super("Player already exists!");
    }
}
