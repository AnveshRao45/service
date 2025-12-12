package com.failfeed.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.failfeed.service.model.Notification;
import com.failfeed.service.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Get all notifications for a user
    List<Notification> findByUser(User user);
    
    // Get unread notifications for a user
    List<Notification> findByUserAndRead(User user, boolean read);
    
    // Get unread notification count for a user
    long countByUserAndRead(User user, boolean read);
    
    // Mark specific notification as read
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.id = :notificationId")
    void markAsRead(@Param("notificationId") Long notificationId);
    
    // Mark all notifications as read for a user
    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.read = true WHERE n.user = :user AND n.read = false")
    void markAllAsRead(@Param("user") User user);
}