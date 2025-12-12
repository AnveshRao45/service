package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.failfeed.service.dto.NotificationDto;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.model.Notification;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.NotificationRepository;
import com.failfeed.service.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationServiceInterface {

    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;

    public NotificationServiceImpl(NotificationRepository notificationRepo, UserRepository userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void createFollowNotification(Long targetUserId, Long actorUserId) {
        User targetUser = userRepo.findById(targetUserId)
            .orElseThrow(() -> new UserNotFoundException(targetUserId));
        User actorUser = userRepo.findById(actorUserId)
            .orElseThrow(() -> new UserNotFoundException(actorUserId));

        String message = actorUser.getName() + " started following you";
        Notification notification = new Notification(targetUser, actorUser, "FOLLOW", message, actorUserId);
        notificationRepo.save(notification);
    }

    @Override
    @Transactional
    public void createLikeNotification(Long postOwnerId, Long actorUserId, Long postId) {
        User postOwner = userRepo.findById(postOwnerId)
            .orElseThrow(() -> new UserNotFoundException(postOwnerId));
        User actorUser = userRepo.findById(actorUserId)
            .orElseThrow(() -> new UserNotFoundException(actorUserId));

        String message = actorUser.getName() + " liked your post";
        Notification notification = new Notification(postOwner, actorUser, "LIKE", message, postId);
        notificationRepo.save(notification);
    }

    @Override
    public List<NotificationDto> getUserNotifications(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        return notificationRepo.findByUser(user).stream()
            .map(NotificationDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getUnreadNotifications(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        return notificationRepo.findByUserAndRead(user, false).stream()
            .map(NotificationDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public long getUnreadCount(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        return notificationRepo.countByUserAndRead(user, false);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        notificationRepo.markAsRead(notificationId);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        notificationRepo.markAllAsRead(user);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepo.deleteById(notificationId);
    }
}