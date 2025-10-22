package com.example.timesheetreport.controllers.response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponse {
    public <T> ResponseEntity<Response<T>> success(Integer statusCode, String message, T data) {
        return ResponseEntity.status(statusCode).body(new Response<>("Success", statusCode, message, data));
    }

    public <T> ResponseEntity<Response<T>> success(Integer statusCode, String message) {
        return ResponseEntity.status(statusCode).body(new Response<>("Success", statusCode, message));
    }

    public <T> ResponseEntity<Response<T>> error(Integer statusCode, String message, String errorMessage) {
        return ResponseEntity.status(statusCode).body(new Response<>("Error", statusCode, message, errorMessage));
    }
}
