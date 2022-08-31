package com.fortunate.activitytracker.controller;

import com.fortunate.activitytracker.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {

    // For UI Pages
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(UserNotFoundException ex) {
        return "error";
    }

    // For REST APIs
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}