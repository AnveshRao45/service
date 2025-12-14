package com.failfeed.service.observer;

import com.failfeed.service.model.Alert;
import com.failfeed.service.model.User;


/**
 * Concrete observer for sending alerts to followers.
 */
public class NotificationObserver implements AlertObserver {
    private User observer;

    public NotificationObserver(User observer) {
        this.observer = observer;
    }

    @Override
    public void update(Alert alert) {
        
        System.out.println("Observer '" + observer.getName() + "' received alert: " + alert.getContent());
    }

    public User getObserver() {
        return observer;
    }


}
