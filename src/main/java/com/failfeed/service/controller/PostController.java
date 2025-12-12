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
import com.failfeed.service.service.ManageAlertsServiceInterface;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostServiceInterface postService;

    private final ManageAlertsServiceInterface manageAlertsService;

    public PostController(PostServiceInterface postService, ManageAlertsServiceInterface manageAlertsService) {
        this.postService = postService;
        this.manageAlertsService = manageAlertsService;
    }

    @PostMapping("/create")
    public PostDto createPost(@RequestParam Long userId, @RequestParam String message) {
        PostDto postDto = postService.createPost(userId, message);
        manageAlertsService.createPostAlert(userId);
        return postDto;
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
