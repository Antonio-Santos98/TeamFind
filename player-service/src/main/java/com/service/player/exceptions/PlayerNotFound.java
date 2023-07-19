package com.service.player.exceptions;

public class PlayerNotFound extends RuntimeException{
    public PlayerNotFound(Long userId){super("Player not found for Id - " + userId);}
}
