package com.failfeed.service.observer;

import com.failfeed.service.dto.AlertDto;

/**
 * Observer interface for alert notifications.
 * Implementations will be notified when alerts are created or updated.
 */
public interface AlertObserver {
    void update(AlertDto alert);
    String getName();
}
