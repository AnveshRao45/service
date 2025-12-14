package com.failfeed.service.state;

/**
 * Context class for Alert State pattern.
 * Manages the state of an alert and delegates behavior to state objects.
 */
public class AlertContext {
    private AlertState state;
    // private Long alertId;

    public AlertContext() {
        // this.alertId = alertId;
        this.state = new PendingState();  // Initial state
    }

    public void setState(AlertState state) {
        this.state = state;
        System.out.println("Alert state transitioned to " + state.getStateName() + " state");
    }

    public AlertState getState() {
        return state;
    }

    public String getStateName() {
        return state.getStateName();
    }

    public void send() {
        state.send();
        if (state.getStateName().equals("PENDING")) {
            setState(new SentState());
        }
    }

    public void deliver() {
        state.deliver();
        if (state.getStateName().equals("SENT")) {
            setState(new DeliveredState());
        }
    }
}
