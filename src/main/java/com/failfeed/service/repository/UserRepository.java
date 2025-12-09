package com.failfeed.service.repository;

import com.failfeed.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find users who are following a specific user (for efficient follower queries)
    @Query("SELECT u FROM User u JOIN u.following f WHERE f.id = :targetId")
    List<User> findFollowersByTargetId(@Param("targetId") Long targetId);
}
