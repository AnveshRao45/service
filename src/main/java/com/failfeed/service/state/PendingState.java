package com.failfeed.service.state;

/**
 * Pending state - initial state when alert is created.
 */
public class PendingState implements AlertState {

    @Override
    public void send() {
        System.out.println("Alert is being sent from PENDING state");
    }

    @Override
    public void deliver() {
        System.out.println("Cannot deliver alert in PENDING state. Alert must be sent first.");
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }
}
