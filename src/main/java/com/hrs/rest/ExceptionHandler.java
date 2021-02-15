package com.hrs.rest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    //TODO : //To be continued... Other exceptions ..Check cause also ..
    @org.springframework.web.bind.annotation.ExceptionHandler({
            IllegalArgumentException.class,
            EntityNotFoundException.class,
            EntityExistsException.class,
            DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleException (RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Error : " + ex.getMessage ();
        return handleExceptionInternal (ex, bodyOfResponse,
                new HttpHeaders (), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String bodyOfResponse = "Error : " + ex.getMessage ();
        return handleExceptionInternal (ex, bodyOfResponse,
                new HttpHeaders (), HttpStatus.BAD_REQUEST, request);
    }
}

