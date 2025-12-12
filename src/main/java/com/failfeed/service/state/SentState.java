package com.failfeed.service.state;

/**
 * Sent state - alert has been sent but not yet delivered.
 */
public class SentState implements AlertState {

    @Override
    public void send() {
        System.out.println("Alert already sent. Resending...");
    }

    @Override
    public void deliver() {
        System.out.println("Alert is being delivered from SENT state");
    }

    @Override
    public String getStateName() {
        return "SENT";
    }
}
