package com.service.team.exceptions;

public class TeamNotFound extends RuntimeException{
    public TeamNotFound(Long teamId){super("Team Not Found with Id of " + teamId);}
}
