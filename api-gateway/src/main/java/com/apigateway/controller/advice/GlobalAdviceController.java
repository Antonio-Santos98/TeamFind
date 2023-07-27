package com.apigateway.controller.advice;


import com.apigateway.exceptions.IncorrectPasswordException;
import com.apigateway.exceptions.UserExists;
import com.apigateway.exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String incorrectPass(IncorrectPasswordException iPW){
        return iPW.getLocalizedMessage();
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundUser(UserNotFound nFU){
        return nFU.getLocalizedMessage();
    }

    @ExceptionHandler(UserExists.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userExists(UserExists uEx){
        return uEx.getLocalizedMessage();
    }
}
