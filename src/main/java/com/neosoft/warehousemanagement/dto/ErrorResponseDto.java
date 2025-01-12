package com.neosoft.warehousemanagement.dto;


public class ErrorResponseDto {

    private int statusCode;
    private String message;

    public ErrorResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
