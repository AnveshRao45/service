package com.failfeed.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private User user;

    public Post() {}

    public Post(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public User getUser() { return user; }
}
