package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.UserDto;
import com.failfeed.service.service.UserServiceInterface;

@RestController
@RequestMapping("/users")
public class UserManagementController {

    private final UserServiceInterface userService;

    public UserManagementController(UserServiceInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDto createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/followers")
    public List<UserDto> getFollowers(@PathVariable Long id) {
        return userService.getFollowers(id);
    }

    @GetMapping("/{id}/following")
    public List<UserDto> getFollowing(@PathVariable Long id) {
        return userService.getFollowing(id);
    }
}