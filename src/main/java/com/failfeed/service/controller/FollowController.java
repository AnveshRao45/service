package com.failfeed.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.UserDto;
import com.failfeed.service.service.UserServiceInterface;


@RestController
@RequestMapping("/users")
public class FollowController {

    private final UserServiceInterface userService;

    public FollowController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/{followerId}/follow/{targetId}")
    public UserDto follow(@PathVariable Long followerId, @PathVariable Long targetId) {
        UserDto userDto = userService.followUser(followerId, targetId);
        
        return userDto;
    }

    @PostMapping("/{followerId}/unfollow/{targetId}")
    public UserDto unfollow(@PathVariable Long followerId, @PathVariable Long targetId) {
        return userService.unfollowUser(followerId, targetId);
    }
}