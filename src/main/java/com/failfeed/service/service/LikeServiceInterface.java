package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.LikeDto;

public interface LikeServiceInterface {
    
    LikeDto likePost(Long userId, Long postId);
    void unlikePost(Long userId, Long postId);
    long getLikeCount(Long postId);
    List<LikeDto> getLikesForPost(Long postId);
    List<LikeDto> getLikesByUser(Long userId);
    boolean isPostLikedByUser(Long userId, Long postId);
}