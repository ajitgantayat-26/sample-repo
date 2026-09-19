package com.emp.employeeManagement.controller;

import com.emp.employeeManagement.Exception.DuplicateIdException;
import com.emp.employeeManagement.Exception.ErrorMessage;
import com.emp.employeeManagement.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleUsernotFoundException(UserNotFoundException ex){
        ErrorMessage error= new ErrorMessage(ex.getMessage(),"Employee not Found", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorMessage>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalServerError(Exception ex){
        ErrorMessage error = new ErrorMessage(ex.getMessage(),"Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateIdException(DuplicateIdException ex){
        ErrorMessage error = new ErrorMessage(ex.getMessage(),"Duplicate Id present",HttpStatus.FOUND.value());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
}
