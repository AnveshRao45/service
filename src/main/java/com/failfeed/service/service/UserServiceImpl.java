package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.failfeed.service.dto.UserDto;
import com.failfeed.service.exception.InvalidFollowOperationException;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDto createUser(String name) {
        User user = new User(name);
        User savedUser = userRepo.save(user);
        return new UserDto(savedUser);
    }

    @Override
    public UserDto followUser(Long followerId, Long targetId) {
        User follower = userRepo.findById(followerId)
            .orElseThrow(() -> new UserNotFoundException(followerId));
        User target = userRepo.findById(targetId)
            .orElseThrow(() -> new UserNotFoundException(targetId));

        // Prevent self-following
        if (followerId.equals(targetId)) {
            throw new InvalidFollowOperationException("Users cannot follow themselves");
        }

        // Check if already following
        if (follower.getFollowing().contains(target)) {
            throw new InvalidFollowOperationException("User " + followerId + " is already following user " + targetId);
        }

        follower.getFollowing().add(target);
        User savedFollower = userRepo.save(follower);
        return new UserDto(savedFollower);
    }

    @Override
    @Transactional
    public UserDto unfollowUser(Long followerId, Long targetId) {
        User follower = userRepo.findById(followerId)
            .orElseThrow(() -> new UserNotFoundException(followerId));
        User target = userRepo.findById(targetId)
            .orElseThrow(() -> new UserNotFoundException(targetId));

        // Check if the follower is actually following the target
        if (!follower.getFollowing().contains(target)) {
            throw new InvalidFollowOperationException("User " + followerId + " is not following user " + targetId);
        }

        follower.getFollowing().remove(target);
        User savedFollower = userRepo.save(follower);
        return new UserDto(savedFollower);
    }

    @Override
    public List<UserDto> getFollowers(Long userId) {
        // Validate user exists
        userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        // Use efficient database query to find followers
        return userRepo.findFollowersByTargetId(userId).stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getFollowing(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        return user.getFollowing().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        return new UserDto(user);
    }
}