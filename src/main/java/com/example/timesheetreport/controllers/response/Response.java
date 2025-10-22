package com.example.timesheetreport.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String status;
    private Integer statusCode;
    private String message;
    private T data;
    private String errorMessage;
    
    public Response(String status, Integer statusCode, String message) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Response(String status, Integer statusCode, String message, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public Response(String status, Integer statusCode, String message, String errorMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errorMessage = errorMessage;
    }
}
