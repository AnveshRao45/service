package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.NotificationDto;
import com.failfeed.service.service.NotificationServiceInterface;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationServiceInterface notificationService;

    public NotificationController(NotificationServiceInterface notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<NotificationDto> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/{userId}/unread")
    public List<NotificationDto> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @GetMapping("/{userId}/unread/count")
    public long getUnreadCount(@PathVariable Long userId) {
        return notificationService.getUnreadCount(userId);
    }

    @PostMapping("/{notificationId}/read")
    public void markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
    }

    @PostMapping("/{userId}/read-all")
    public void markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
    }
}