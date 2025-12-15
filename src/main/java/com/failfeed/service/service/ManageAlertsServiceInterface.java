package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.AlertDto;


public interface ManageAlertsServiceInterface {
    void createPostAlert(Long userId);
    void createFollowAlert(Long followerId, Long followedId);
    void createRetweetAlert(Long retweeterId, Long originalPosterId);
    void createLikeAlert(Long userId, Long originalPosterId);
    AlertDto getAlertById(Long alertId);   
    List<AlertDto> displayAlerts(Long userId);
    int getAlertsCount(Long userId);
}
