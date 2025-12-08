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
        User follower = userRepo.findById(followerId).orElseThrow();
        User target = userRepo.findById(targetId).orElseThrow();

        follower.getFollowing().add(target);
        return userRepo.save(follower);
    }

    public List<User> getFollowers(Long userId) {
        User target = userRepo.findById(userId).orElseThrow();

        return userRepo.findAll().stream()
                .filter(u -> u.getFollowing().contains(target))
                .collect(Collectors.toList());
    }

    public List<User> getFollowing(Long userId) {
        return userRepo.findById(userId).orElseThrow().getFollowing().stream().toList();
    }
}
