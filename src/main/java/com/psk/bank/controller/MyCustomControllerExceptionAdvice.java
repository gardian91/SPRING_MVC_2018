package com.psk.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.psk.bank.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class MyCustomControllerExceptionAdvice {

	
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceNotFoundException.class)
    public @ResponseBody String handlerNotFoundException(ResourceNotFoundException ex) {
        return "GENERAL CUSTOM ADVICE " + ex.getMessage();
    }
	
}
