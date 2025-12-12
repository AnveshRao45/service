package com.failfeed.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.failfeed.service.model.Like;
import com.failfeed.service.model.Post;
import com.failfeed.service.model.User;

public interface LikeRepository extends JpaRepository<Like, Long> {
    
    // Check if user already liked a post
    Optional<Like> findByUserAndPost(User user, Post post);
    
    // Count likes for a post
    long countByPost(Post post);
    
    // Get all likes for a post
    List<Like> findByPost(Post post);
    
    // Get all likes by a user
    List<Like> findByUser(User user);
    
    // Check if user liked post (for validation)
    boolean existsByUserAndPost(User user, Post post);
    
    // Delete like by user and post
    void deleteByUserAndPost(User user, Post post);
}