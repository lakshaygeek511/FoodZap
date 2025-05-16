package com.foodzap.dto;

import java.time.LocalDateTime;

/**
 * Standardized error response for REST API
 */
public class ErrorResponse {
    private String message;
    private int status;
    private String timestamp;
    
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now().toString();
    }
    
    public ErrorResponse(String message) {
        this();
        this.message = message;
    }
    
    public ErrorResponse(String message, int status) {
        this(message);
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
} 