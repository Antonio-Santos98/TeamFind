package com.apigateway.exceptions;

public class UserExists extends RuntimeException{
    public UserExists(String userName){
        super("Username already exists with name: " + userName);
    }
}
