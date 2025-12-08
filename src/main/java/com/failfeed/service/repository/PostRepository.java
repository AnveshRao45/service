package com.failfeed.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.failfeed.service.model.Post;
import com.failfeed.service.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
