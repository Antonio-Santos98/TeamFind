package com.apigateway.exceptions;


public class UserNotFound extends RuntimeException{
    public UserNotFound(String userName){
        super("User not found with username: " + userName);
    }
}
