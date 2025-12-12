package com.failfeed.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.failfeed.service.model.Retweet;
import com.failfeed.service.model.User;
import com.failfeed.service.model.Post;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    List<Retweet> findByUser(User user);
    List<Retweet> findByOriginalPost(Post post);
    List<Retweet> findByUserOrderByRetweetedAtDesc(User user);
    Optional<Retweet> findByUserAndOriginalPost(User user, Post post);
    long countByOriginalPost(Post post);
}
