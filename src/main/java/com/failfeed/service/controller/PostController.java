package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.PostDto;
import com.failfeed.service.service.PostServiceInterface;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostServiceInterface postService;

    public PostController(PostServiceInterface postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public PostDto createPost(@RequestParam Long userId, @RequestParam String message) {
        return postService.createPost(userId, message);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/feed/{userId}")
    public List<PostDto> getFeed(@PathVariable Long userId) {
        return postService.getFeed(userId);
    }

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }
}
