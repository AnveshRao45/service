package com.failfeed.service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.failfeed.service.model.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    // List<Alert> findByAlert(Alert alert);
    List<Alert> findByUserOrderByCreatedAtDesc(com.failfeed.service.model.User user);
    int countByUserId(Long userId);
}
