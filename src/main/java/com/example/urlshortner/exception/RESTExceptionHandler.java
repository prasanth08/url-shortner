package com.example.urlshortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class RESTExceptionHandler {
    @ExceptionHandler(value = URLNotFoundException.class)
    public ResponseEntity<?> handleUrlNotFound(){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<?> handleInvalidUrl(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
