package com.failfeed.service.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.failfeed.service.model.User;

public class UserDto {
    private Long id;
    private String name;
    private List<Long> followingIds;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        
        if (user.getFollowing() != null) {
            this.followingIds = user.getFollowing().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Long> getFollowingIds() { return followingIds; }
    public void setFollowingIds(List<Long> followingIds) { this.followingIds = followingIds; }
}