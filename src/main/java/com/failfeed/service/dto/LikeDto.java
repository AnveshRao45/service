package com.failfeed.service.dto;

import com.failfeed.service.model.Like;

public class LikeDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long postId;
    private String postMessage;
    
    public LikeDto() {}
    
    public LikeDto(Like like) {
        this.id = like.getId();
        if (like.getUser() != null) {
            this.userId = like.getUser().getId();
            this.userName = like.getUser().getName();
        }
        if (like.getPost() != null) {
            this.postId = like.getPost().getId();
            this.postMessage = like.getPost().getMessage();
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    
    public String getPostMessage() { return postMessage; }
    public void setPostMessage(String postMessage) { this.postMessage = postMessage; }
}