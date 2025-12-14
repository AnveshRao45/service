package com.failfeed.service.observer;

import java.util.ArrayList;
import java.util.List;

import com.failfeed.service.model.Alert;
import com.failfeed.service.repository.AlertRepository;

/**
 * Subject class for Observer pattern.
 * Manages a list of observers and notifies them when alerts change.
 */
public class AlertSubject {
    private List<NotificationObserver> observers = new ArrayList<>();
    private final AlertRepository alertRepo;
    

    public AlertSubject(AlertRepository alertRepo){
        this.alertRepo = alertRepo;
    }

    public void attach(NotificationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer " + observer.getObserver().getName() + " attached");
        }
    }

    public void detach(NotificationObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer " + observer.getObserver().getName() + " detached");
        }
    }

    public void notifyObservers(String alertContent) {
        System.out.println("Notifying " + observers.size() + " observers about alert: " + alertContent);
        for (NotificationObserver observer : observers) {
            Alert alert = new Alert(observer.getObserver(), alertContent);
            observer.update(alert);
            alertRepo.save(alert);
        }
    }

    public List<NotificationObserver> getObservers() {
        return new ArrayList<>(observers);
    }


}
