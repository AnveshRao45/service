package com.failfeed.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.failfeed.service.model.Post;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.PostRepository;
import com.failfeed.service.repository.UserRepository;

@Service
public class PostService {

    private final UserRepository userRepo;
    private final PostRepository postRepo;

    public PostService(UserRepository userRepo, PostRepository postRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    public Post createPost(Long userId, String message) {
        User user = userRepo.findById(userId).orElseThrow();
        Post post = new Post(message, user);
        return postRepo.save(post);
    }

    public List<Post> getFeed(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();

        List<Post> feed = new ArrayList<>();

        // include posts from all users they follow
        for (User u : user.getFollowing()) {
            feed.addAll(postRepo.findByUser(u));
        }

        return feed;
    }
}
