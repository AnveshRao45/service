package com.failfeed.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Retweet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;              // User who retweets

    @ManyToOne
    private Post originalPost;      // Post being retweeted

    private LocalDateTime retweetedAt;

    public Retweet() {}

    public Retweet(User user, Post originalPost) {
        this.user = user;
        this.originalPost = originalPost;
        this.retweetedAt = LocalDateTime.now();
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getOriginalPost() { return originalPost; }
    public void setOriginalPost(Post originalPost) { this.originalPost = originalPost; }

    public LocalDateTime getRetweetedAt() { return retweetedAt; }
    public void setRetweetedAt(LocalDateTime retweetedAt) { this.retweetedAt = retweetedAt; }
}
