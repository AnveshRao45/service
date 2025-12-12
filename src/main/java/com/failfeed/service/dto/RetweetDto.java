package com.failfeed.service.dto;

import com.failfeed.service.model.Retweet;
import java.time.LocalDateTime;

public class RetweetDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long originalPostId;
    private String originalPostMessage;
    private String originalPostAuthor;
    private LocalDateTime retweetedAt;

    public RetweetDto() {}

    public RetweetDto(Retweet retweet) {
        this.id = retweet.getId();
        this.userId = retweet.getUser().getId();
        this.userName = retweet.getUser().getName();
        this.originalPostId = retweet.getOriginalPost().getId();
        this.originalPostMessage = retweet.getOriginalPost().getMessage();
        this.originalPostAuthor = retweet.getOriginalPost().getUser().getName();
        this.retweetedAt = retweet.getRetweetedAt();
    }

    // Constructor with all parameters
    public RetweetDto(Long id, Long userId, String userName, Long originalPostId, 
                     String originalPostMessage, String originalPostAuthor, LocalDateTime retweetedAt) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.originalPostId = originalPostId;
        this.originalPostMessage = originalPostMessage;
        this.originalPostAuthor = originalPostAuthor;
        this.retweetedAt = retweetedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getOriginalPostId() { return originalPostId; }
    public void setOriginalPostId(Long originalPostId) { this.originalPostId = originalPostId; }

    public String getOriginalPostMessage() { return originalPostMessage; }
    public void setOriginalPostMessage(String originalPostMessage) { this.originalPostMessage = originalPostMessage; }

    public String getOriginalPostAuthor() { return originalPostAuthor; }
    public void setOriginalPostAuthor(String originalPostAuthor) { this.originalPostAuthor = originalPostAuthor; }

    public LocalDateTime getRetweetedAt() { return retweetedAt; }
    public void setRetweetedAt(LocalDateTime retweetedAt) { this.retweetedAt = retweetedAt; }
}
