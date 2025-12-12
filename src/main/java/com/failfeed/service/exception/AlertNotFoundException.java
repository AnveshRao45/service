package com.failfeed.service.exception;

public class AlertNotFoundException extends RuntimeException {
    public AlertNotFoundException(Long alertId) {
        super("Alert not found with id: " + alertId);
    }
    
}
