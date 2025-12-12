package com.failfeed.service.service;

import java.util.List;

import com.failfeed.service.dto.RetweetDto;

public interface RetweetServiceInterface {
    RetweetDto retweetPost(Long userId, Long postId);
    List<RetweetDto> getRetweetsByUser(Long userId);
    List<RetweetDto> getRetweetsForPost(Long postId);
    long getRetweetCount(Long postId);
    void undoRetweet(Long userId, Long postId);
    boolean hasUserRetweeted(Long userId, Long postId);
}
