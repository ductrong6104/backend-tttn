package org.example.dienluc.errors;

import org.example.dienluc.payload.ResponseData;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = "Data integrity violation: " + ex.getRootCause().getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Data integrity violation")
                .status(404)
                .build();
        return ResponseEntity.ok(responseData);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = "Constraint violation: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("Constraint violation")
                .status(404)
                .build();
        return ResponseEntity.ok(responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseData responseData = ResponseData.builder()
                .data(errors)
                .message("Not valid argument in method")
                .status(404)
                .build();
        return ResponseEntity.ok(responseData);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        ResponseData responseData = ResponseData.builder()
                .data(errorMessage)
                .message("An error occurred:")
                .status(500)
                .build();
        return ResponseEntity.ok(responseData);
    }
}
