package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.RetweetDto;
import com.failfeed.service.service.RetweetServiceInterface;

@RestController
@RequestMapping("/retweets")
public class RetweetController {

    private final RetweetServiceInterface retweetService;

    public RetweetController(RetweetServiceInterface retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping("/create")
    public RetweetDto retweetPost(@RequestParam Long userId, @RequestParam Long postId) {
        return retweetService.retweetPost(userId, postId);
    }

    @GetMapping("/user/{userId}")
    public List<RetweetDto> getRetweetsByUser(@PathVariable Long userId) {
        return retweetService.getRetweetsByUser(userId);
    }

    @GetMapping("/post/{postId}")
    public List<RetweetDto> getRetweetsForPost(@PathVariable Long postId) {
        return retweetService.getRetweetsForPost(postId);
    }

    @GetMapping("/count/{postId}")
    public long getRetweetCount(@PathVariable Long postId) {
        return retweetService.getRetweetCount(postId);
    }

    @DeleteMapping("/undo")
    public void undoRetweet(@RequestParam Long userId, @RequestParam Long postId) {
        retweetService.undoRetweet(userId, postId);
    }

    @GetMapping("/check")
    public boolean hasUserRetweeted(@RequestParam Long userId, @RequestParam Long postId) {
        return retweetService.hasUserRetweeted(userId, postId);
    }
}
