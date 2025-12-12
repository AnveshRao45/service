package com.failfeed.service.observer;

import com.failfeed.service.dto.AlertDto;
import java.util.ArrayList;
import java.util.List;

/**
 * Subject class for Observer pattern.
 * Manages a list of observers and notifies them when alerts change.
 */
public class AlertSubject {
    private List<AlertObserver> observers = new ArrayList<>();

    public void attach(AlertObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer " + observer.getName() + " attached");
        }
    }

    public void detach(AlertObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer " + observer.getName() + " detached");
        }
    }

    public void notifyObservers(AlertDto alert) {
        System.out.println("Notifying " + observers.size() + " observers about alert: " + alert.getContent());
        for (AlertObserver observer : observers) {
            observer.update(alert);
        }
    }

    public List<AlertObserver> getObservers() {
        return new ArrayList<>(observers);
    }
}
