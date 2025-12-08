package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.model.User;
import com.failfeed.service.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    @PostMapping("/{followerId}/follow/{targetId}")
    public String follow(@PathVariable Long followerId, @PathVariable Long targetId) {
        userService.followUser(followerId, targetId);
        return "User " + followerId + " now follows " + targetId;
    }

    @PostMapping("/{followerId}/unfollow/{targetId}")
    public String unfollow(@PathVariable Long followerId, @PathVariable Long targetId) {
        userService.unfollowUser(followerId, targetId);
        return "User " + followerId + " unfollowed " + targetId;
    }

    @GetMapping("/{id}/followers")
    public List<User> getFollowers(@PathVariable Long id) {
        return userService.getFollowers(id);
    }

    @GetMapping("/{id}/following")
    public List<User> getFollowing(@PathVariable Long id) {
        return userService.getFollowing(id);
    }
}
