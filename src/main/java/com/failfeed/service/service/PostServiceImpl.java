package com.failfeed.service.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.failfeed.service.dto.PostDto;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.model.Post;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.PostRepository;
import com.failfeed.service.repository.UserRepository;

@Service
public class PostServiceImpl implements PostServiceInterface {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final LikeServiceInterface likeService;

    public PostServiceImpl(UserRepository userRepo, PostRepository postRepo, LikeServiceInterface likeService) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.likeService = likeService;
    }

    @Override
    @Transactional
    public PostDto createPost(Long userId, String message) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = new Post(message, user);
        Post savedPost = postRepo.save(post);
        return new PostDto(savedPost);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return new PostDto(post, likeService.getLikeCount(postId));
    }

    @Override
    public List<PostDto> getFeed(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        List<Post> feed = new ArrayList<>();

        // include posts from all users they follow
        for (User followedUser : user.getFollowing()) {
            feed.addAll(postRepo.findByUser(followedUser));
        }

        return feed.stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed()) // Newest first
            .map(post -> new PostDto(post, likeService.getLikeCount(post.getId())))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getUserPosts(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        return postRepo.findByUser(user).stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed()) // Newest first
            .map(post -> new PostDto(post, likeService.getLikeCount(post.getId())))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepo.findAll().stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed()) // Newest first
            .map(post -> new PostDto(post, likeService.getLikeCount(post.getId())))
            .collect(Collectors.toList());
    }
}