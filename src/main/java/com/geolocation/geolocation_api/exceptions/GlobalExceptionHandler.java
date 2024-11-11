package com.geolocation.geolocation_api.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.geolocation.geolocation_api.exceptions.user.EmailAlreadyExistsException;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        if(ex.getCause() instanceof ConstraintViolationException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("a constraint has been violated. --> "+ex.getMessage()) ;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred.");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
