package com.service.team.controller.advice;

import com.service.team.exceptions.RequestExistsException;
import com.service.team.exceptions.TeamExistsException;
import com.service.team.exceptions.TeamNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(RequestExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String requestExists(RequestExistsException rEx){return rEx.getLocalizedMessage();}

    @ExceptionHandler(TeamExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String teamExists(TeamExistsException tEx){return tEx.getLocalizedMessage();}

    @ExceptionHandler(TeamNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String teamNotFound(TeamNotFound tNF){return tNF.getLocalizedMessage();}

    //General Exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String generalException(Exception ex){return "Server error exception: " + ex;}
}
