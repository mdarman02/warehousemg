package com.neosoft.warehousemanagement.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponse(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}