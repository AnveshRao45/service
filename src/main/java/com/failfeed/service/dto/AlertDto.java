package com.failfeed.service.dto;
import com.failfeed.service.model.Alert;

import java.time.LocalDateTime;


public class AlertDto {
    private Long id;
    private String content;
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;

    public AlertDto() {}

    public AlertDto(Alert alert) {
        this.id = alert.getId();
        this.content = alert.getContent();
        this.userId = alert.getUser().getId();
        this.userName = alert.getUser().getName();
        this.createdAt = alert.getCreatedAt();
    }

    // Constructor matching: new AlertDto(Long, String, Long, String, LocalDateTime)
    public AlertDto(Long id, String content, Long userId, String userName, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getContent() { 
        return content; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }

    public Long getUserId() {
         return userId; 
    }
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getUserName() { 
        return userName; 
    }
    public void setUserName(String userName) { 
        this.userName = userName; 
    }
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
}
