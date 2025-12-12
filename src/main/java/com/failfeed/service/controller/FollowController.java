package com.failfeed.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.UserDto;
import com.failfeed.service.service.UserServiceInterface;
import com.failfeed.service.service.ManageAlertsServiceInterface;

@RestController
@RequestMapping("/users")
public class FollowController {

    private final UserServiceInterface userService;

    private final ManageAlertsServiceInterface manageAlertsService;

    public FollowController(UserServiceInterface userService, ManageAlertsServiceInterface manageAlertsService) {
        this.userService = userService;
        this.manageAlertsService = manageAlertsService;
    }

    @PostMapping("/{followerId}/follow/{targetId}")
    public UserDto follow(@PathVariable Long followerId, @PathVariable Long targetId) {
        UserDto userDto = userService.followUser(followerId, targetId);
        manageAlertsService.createFollowAlert(followerId, targetId);
        return userDto;
    }

    @PostMapping("/{followerId}/unfollow/{targetId}")
    public UserDto unfollow(@PathVariable Long followerId, @PathVariable Long targetId) {
        return userService.unfollowUser(followerId, targetId);
    }
}