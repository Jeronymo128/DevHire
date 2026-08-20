package com.devhire.exception;

import java.util.Map;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationErrors(
        MethodArgumentNotValidException exception) {
            
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

                return ResponseEntity.badRequest().body(errors);

}
@ExceptionHandler(HttpMessageNotReadableException.class)
public ResponseEntity<Map<String, String>> handleInvalidJson(
        HttpMessageNotReadableException exception) {

    Map<String, String> error = new HashMap<>();
    error.put("error", "Invalid request data");

    return ResponseEntity.badRequest().body(error);
}
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<Map<String, String>> handleIllegalArgument(
        IllegalArgumentException exception) {

    Map<String, String> error = new HashMap<>();
    error.put("error", exception.getMessage());

    return ResponseEntity.status(409).body(error);
}

}