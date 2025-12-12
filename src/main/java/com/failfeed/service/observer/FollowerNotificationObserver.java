package com.failfeed.service.observer;

import com.failfeed.service.dto.AlertDto;

/**
 * Concrete observer for sending alerts to followers.
 */
public class FollowerNotificationObserver implements AlertObserver {
    private String followerName;
    private Long followerId;

    public FollowerNotificationObserver(Long followerId, String followerName) {
        this.followerId = followerId;
        this.followerName = followerName;
    }

    @Override
    public void update(AlertDto alert) {
        System.out.println("Follower '" + followerName + "' received alert: " + alert.getContent());
        // In a real application, this would persist the alert for the follower
    }

    @Override
    public String getName() {
        return "FollowerObserver-" + followerId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public String getFollowerName() {
        return followerName;
    }
}
