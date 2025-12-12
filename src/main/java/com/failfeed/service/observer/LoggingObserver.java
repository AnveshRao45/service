package com.failfeed.service.observer;

import com.failfeed.service.dto.AlertDto;

/**
 * Concrete observer for logging alerts.
 */
public class LoggingObserver implements AlertObserver {

    @Override
    public void update(AlertDto alert) {
        System.out.println("[ALERT LOG] Alert ID: " + alert.getId() + 
                         ", Content: " + alert.getContent() + 
                         ", User: " + alert.getUserName() + 
                         ", Created: " + alert.getCreatedAt());
    }

    @Override
    public String getName() {
        return "LoggingObserver";
    }
}
