package com.failfeed.service.state;

/**
 * State interface for Alert states.
 * Defines behavior that varies depending on the alert's state.
 */
public interface AlertState {
    void send();
    void deliver();
    String getStateName();
}
