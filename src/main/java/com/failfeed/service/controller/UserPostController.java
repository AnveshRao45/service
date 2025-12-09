package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.PostDto;
import com.failfeed.service.service.PostServiceInterface;

@RestController
@RequestMapping("/users")
public class UserPostController {

    private final PostServiceInterface postService;

    public UserPostController(PostServiceInterface postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}/posts")
    public List<PostDto> getUserPosts(@PathVariable Long id) {
        return postService.getUserPosts(id);
    }
}