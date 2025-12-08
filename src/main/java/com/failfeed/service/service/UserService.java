package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.failfeed.service.model.User;
import com.failfeed.service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(String name) {
        User user = new User(name);
        return userRepo.save(user);
    }

    public User followUser(Long followerId, Long targetId) {
        User follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
        User target = userRepo.findById(targetId).orElseThrow(() -> new RuntimeException("Target user not found"));

        // Prevent self-following
        if (followerId.equals(targetId)) {
            throw new RuntimeException("Users cannot follow themselves");
        }

        // Check if already following
        if (follower.getFollowing().contains(target)) {
            throw new RuntimeException("User " + followerId + " is already following user " + targetId);
        }

        follower.getFollowing().add(target);
        return userRepo.save(follower);
    }

    public List<User> getFollowers(Long userId) {
        User target = userRepo.findById(userId).orElseThrow();

        return userRepo.findAll().stream()
                .filter(u -> u.getFollowing().contains(target))
                .collect(Collectors.toList());
    }

    public User unfollowUser(Long followerId, Long targetId) {
        User follower = userRepo.findById(followerId).orElseThrow(() -> new RuntimeException("Follower not found"));
        User target = userRepo.findById(targetId).orElseThrow(() -> new RuntimeException("Target user not found"));

        // Check if the follower is actually following the target
        if (!follower.getFollowing().contains(target)) {
            throw new RuntimeException("User " + followerId + " is not following user " + targetId);
        }

        follower.getFollowing().remove(target);
        return userRepo.save(follower);
    }

    public List<User> getFollowing(Long userId) {
        return userRepo.findById(userId).orElseThrow().getFollowing().stream().toList();
    }
}
