package com.kalbim.vkapppairsgame.error.handling;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

//    @ExceptionHandler( value = EmptyResultDataAccessException.class)
//    protected ResponseEntity<String> handleConflict(
//            RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "Empty result, 0 players found";
//        return new ResponseEntity<String>(bodyOfResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler( value = InvalidDataAccessApiUsageException.class)
//    protected ResponseEntity<String> handleInvalidDataAccessApiUsageException(
//            RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "Vk key validation failed";
//        return new ResponseEntity<String>(bodyOfResponse, HttpStatus.BAD_REQUEST);
//    }
}
