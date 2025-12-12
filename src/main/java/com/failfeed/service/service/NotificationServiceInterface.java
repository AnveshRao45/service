package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.NotificationDto;

public interface NotificationServiceInterface {
    
    void createFollowNotification(Long targetUserId, Long actorUserId);
    void createLikeNotification(Long postOwnerId, Long actorUserId, Long postId);
    
    List<NotificationDto> getUserNotifications(Long userId);
    List<NotificationDto> getUnreadNotifications(Long userId);
    long getUnreadCount(Long userId);
    
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    
    void deleteNotification(Long notificationId);
}