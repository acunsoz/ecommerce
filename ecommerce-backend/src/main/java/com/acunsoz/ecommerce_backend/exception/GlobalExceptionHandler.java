package com.acunsoz.ecommerce_backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request)
    {
        Map<String,Object> body = new HashMap<>();

        body.put("message", ex.getReason());
        body.put("status", ex.getStatusCode().value());
        body.put("timestamp", System.currentTimeMillis());
        body.put("path",request.getRequestURI());

        return new ResponseEntity<>(body,ex.getStatusCode());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex,HttpServletRequest request)
    {
        Map<String,Object> body = new HashMap<>();

        body.put("message",ex.getMessage());
        body.put("status",404);
        body.put("timestamp",System.currentTimeMillis());
        body.put("path",request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }

}
