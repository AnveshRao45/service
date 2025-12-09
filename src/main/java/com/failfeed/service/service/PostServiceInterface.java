package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.PostDto;

public interface PostServiceInterface {
    PostDto createPost(Long userId, String message);
    List<PostDto> getFeed(Long userId);
    List<PostDto> getUserPosts(Long userId);
    List<PostDto> getAllPosts();
}