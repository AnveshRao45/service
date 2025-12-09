package com.failfeed.service.dto;

import com.failfeed.service.model.Post;

public class PostDto {
    private Long id;
    private String message;
    private Long userId;
    private String userName;

    public PostDto() {}

    public PostDto(Post post) {
        this.id = post.getId();
        this.message = post.getMessage();
        if (post.getUser() != null) {
            this.userId = post.getUser().getId();
            this.userName = post.getUser().getName();
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
}