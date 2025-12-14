package com.failfeed.service.observer;

import com.failfeed.service.model.Alert;

/**
 * Observer interface for alert notifications.
 * Implementations will be notified when alerts are created or updated.
 */
public interface AlertObserver {
    void update(Alert alert);
    //String getName();
}
