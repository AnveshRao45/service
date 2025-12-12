package com.failfeed.service.service;

import java.util.List;
import com.failfeed.service.dto.AlertDto;


public interface ManageAlertsServiceInterface {
    AlertDto createPostAlert(Long userId);
    AlertDto createFollowAlert(Long followerId, Long followedId);
    AlertDto createRetweetAlert(Long retweeterId, Long originalPosterId);
    AlertDto getAlertById(Long alertId);   
    void sendAlertToFollowers(Long alertId, Long userId);
    List<AlertDto> displayAlerts(Long userId);
    int getAlertsCount(Long userId);
}
