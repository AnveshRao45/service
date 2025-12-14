package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.LikeDto;
import com.failfeed.service.service.LikeServiceInterface;
import com.failfeed.service.service.ManageAlertsServiceInterface;

@RestController
@RequestMapping("/posts")
public class LikeController {

    private final LikeServiceInterface likeService;
    private final ManageAlertsServiceInterface manageAlertsService;

    public LikeController(LikeServiceInterface likeService, ManageAlertsServiceInterface manageAlertsService) {
        this.likeService = likeService;
        this.manageAlertsService = manageAlertsService;
    }

    @PostMapping("/{postId}/like/{userId}")
    public LikeDto likePost(@PathVariable Long postId, @PathVariable Long userId) {
        LikeDto likeDto = likeService.likePost(userId, postId);
        // Create like alert for the post owner (if not liking own post)
        try {
            manageAlertsService.createLikeAlert(userId, postId);
        } catch (Exception e) {
            // Ignore if alert creation fails (e.g., user likes own post)
        }
        return likeDto;
    }

    @DeleteMapping("/{postId}/like/{userId}")
    public void unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.unlikePost(userId, postId);
    }

    @GetMapping("/{postId}/likes")
    public List<LikeDto> getLikesForPost(@PathVariable Long postId) {
        return likeService.getLikesForPost(postId);
    }

    @GetMapping("/user/{userId}/likes")
    public List<LikeDto> getLikesByUser(@PathVariable Long userId) {
        return likeService.getLikesByUser(userId);
    }
}