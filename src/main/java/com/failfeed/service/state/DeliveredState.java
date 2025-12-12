package com.failfeed.service.state;

/**
 * Delivered state - alert has been successfully delivered.
 */
public class DeliveredState implements AlertState {

    @Override
    public void send() {
        System.out.println("Cannot send alert in DELIVERED state. Alert already delivered.");
    }

    @Override
    public void deliver() {
        System.out.println("Alert already delivered.");
    }

    @Override
    public String getStateName() {
        return "DELIVERED";
    }
}
