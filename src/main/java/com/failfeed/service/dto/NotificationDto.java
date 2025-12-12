package com.failfeed.service.dto;

import com.failfeed.service.model.Notification;

public class NotificationDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long actorId;
    private String actorName;
    private String type;
    private String message;
    private Long relatedId;
    private boolean read;
    private String createdAt;
    
    public NotificationDto() {}
    
    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        if (notification.getUser() != null) {
            this.userId = notification.getUser().getId();
            this.userName = notification.getUser().getName();
        }
        if (notification.getActor() != null) {
            this.actorId = notification.getActor().getId();
            this.actorName = notification.getActor().getName();
        }
        this.type = notification.getType();
        this.message = notification.getMessage();
        this.relatedId = notification.getRelatedId();
        this.read = notification.isRead();
        if (notification.getCreatedAt() != null) {
            this.createdAt = notification.getCreatedAt().toString();
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public Long getActorId() { return actorId; }
    public void setActorId(Long actorId) { this.actorId = actorId; }
    
    public String getActorName() { return actorName; }
    public void setActorName(String actorName) { this.actorName = actorName; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Long getRelatedId() { return relatedId; }
    public void setRelatedId(Long relatedId) { this.relatedId = relatedId; }
    
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}