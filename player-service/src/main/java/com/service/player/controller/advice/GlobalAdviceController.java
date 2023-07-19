package com.service.player.controller.advice;

import com.service.player.exceptions.PlayerExistsException;
import com.service.player.exceptions.PlayerNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler(PlayerExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String requestExists(PlayerExistsException pEx){return pEx.getLocalizedMessage();}

    @ExceptionHandler(PlayerNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String playerNotFound(PlayerNotFound pnF){return pnF.getLocalizedMessage();}
}
