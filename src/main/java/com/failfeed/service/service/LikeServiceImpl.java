package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.failfeed.service.dto.LikeDto;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.model.Like;
import com.failfeed.service.model.Post;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.LikeRepository;
import com.failfeed.service.repository.PostRepository;
import com.failfeed.service.repository.UserRepository;

@Service
public class LikeServiceImpl implements LikeServiceInterface {

    private final LikeRepository likeRepo;
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    public LikeServiceImpl(LikeRepository likeRepo, UserRepository userRepo, PostRepository postRepo) {
        this.likeRepo = likeRepo;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    @Transactional
    public LikeDto likePost(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        // Don't create notification if user likes their own post
        if (user.getId().equals(post.getUser().getId())) {
            throw new RuntimeException("Users cannot like their own posts");
        }
        
        // Check if already liked
        if (likeRepo.existsByUserAndPost(user, post)) {
            throw new RuntimeException("User " + userId + " already liked post " + postId);
        }
        
        Like like = new Like(user, post);
        Like savedLike = likeRepo.save(like);
                
        return new LikeDto(savedLike);
    }

    @Override
    @Transactional
    public void unlikePost(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        // Check if actually liked
        if (!likeRepo.existsByUserAndPost(user, post)) {
            throw new RuntimeException("User " + userId + " has not liked post " + postId);
        }
        
        likeRepo.deleteByUserAndPost(user, post);
    }

    @Override
    public long getLikeCount(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        return likeRepo.countByPost(post);
    }

    @Override
    public List<LikeDto> getLikesForPost(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        return likeRepo.findByPost(post).stream()
            .map(LikeDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<LikeDto> getLikesByUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        return likeRepo.findByUser(user).stream()
            .map(LikeDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isPostLikedByUser(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        return likeRepo.existsByUserAndPost(user, post);
    }
}