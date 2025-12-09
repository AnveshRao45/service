package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.UserDto;

public interface UserServiceInterface {
    UserDto createUser(String name);
    UserDto followUser(Long followerId, Long targetId);
    UserDto unfollowUser(Long followerId, Long targetId);
    List<UserDto> getFollowers(Long userId);
    List<UserDto> getFollowing(Long userId);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
}