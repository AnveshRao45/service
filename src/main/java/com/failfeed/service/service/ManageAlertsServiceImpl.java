package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.failfeed.service.dto.AlertDto;
import com.failfeed.service.dto.UserDto;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.exception.AlertNotFoundException;
import com.failfeed.service.model.Alert;
import com.failfeed.service.model.User;
import com.failfeed.service.repository.UserRepository;
import com.failfeed.service.repository.AlertRepository;
import com.failfeed.service.observer.AlertSubject;
import com.failfeed.service.observer.FollowerNotificationObserver;
import com.failfeed.service.observer.LoggingObserver;
import com.failfeed.service.state.AlertContext;


@Service
public class ManageAlertsServiceImpl implements ManageAlertsServiceInterface {

    private final UserRepository userRepo;
    private final AlertRepository alertRepo;
    private final AlertSubject alertSubject;

    public ManageAlertsServiceImpl(UserRepository userRepository, AlertRepository alertRepository) {
        this.userRepo = userRepository;
        this.alertRepo = alertRepository;
        this.alertSubject = new AlertSubject();
        // Attach default observers
        this.alertSubject.attach(new LoggingObserver());
    }
    @Override
    public AlertDto createPostAlert(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        // For simplicity, generating a random alert content
        String content = user.getName() + " has tweeted!";
        Alert alert = new Alert(content, user);
        AlertDto alertDto = new AlertDto(alert);
        alertRepo.save(alert);
        
        // Initialize alert state
        AlertContext alertContext = new AlertContext(alert.getId());
        alertContext.send();
        
        // Notify observers about new alert
        alertSubject.notifyObservers(alertDto);

        sendAlertToFollowers(alert.getId(), userId);
        
        return alertDto;
    }

    @Override
    public AlertDto createFollowAlert(Long followerId, Long followedId) {
        User follower = userRepo.findById(followerId)
            .orElseThrow(() -> new UserNotFoundException(followerId));
        User followed = userRepo.findById(followedId)
            .orElseThrow(() -> new UserNotFoundException(followedId));
        String content = follower.getName() + " started following " + followed.getName();
        Alert alert = new Alert(content, followed);
        AlertDto alertDto = new AlertDto(alert);
        alertRepo.save(alert);
        
        // Initialize alert state
        AlertContext alertContext = new AlertContext(alert.getId());
        alertContext.send();
        
        // Notify observers about new alert
        alertSubject.notifyObservers(alertDto);

        sendAlertToFollowers(alert.getId(), followedId);
        
        return alertDto;
    }

    @Override
    public AlertDto createRetweetAlert(Long retweeterId, Long originalPosterId) {
        User retweeter = userRepo.findById(retweeterId)
            .orElseThrow(() -> new UserNotFoundException(retweeterId));
        User originalPoster = userRepo.findById(originalPosterId)
            .orElseThrow(() -> new UserNotFoundException(originalPosterId));
        String content = retweeter.getName() + " retweeted " + originalPoster.getName() + "'s post";
        Alert alert = new Alert(content, originalPoster);
        AlertDto alertDto = new AlertDto(alert);
        alertRepo.save(alert);
        // Initialize alert state
        AlertContext alertContext = new AlertContext(alert.getId());
        alertContext.send();
        // Notify observers about new alert
        alertSubject.notifyObservers(alertDto); 
        sendAlertToFollowers(alert.getId(), originalPosterId);
        return alertDto;    
    }

    @Override
    public AlertDto getAlertById(Long alertId) {
        // Implementation here
        Alert alert = alertRepo.findById(alertId)
            .orElseThrow(() -> new AlertNotFoundException(alertId));
        return new AlertDto(alert);
    }

    @Override
    public void sendAlertToFollowers(Long alertId, Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        List<UserDto> followers = user.getFollowers().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
        Alert alert = alertRepo.findById(alertId)
            .orElseThrow(() -> new AlertNotFoundException(alertId));
        
        // For each follower, attach an observer to notify them
        followers.forEach(follower -> {
            FollowerNotificationObserver followerObserver = new FollowerNotificationObserver(
                follower.getId(), 
                follower.getName()
            );
            alertSubject.attach(followerObserver);
            
            // Simulate sending alert
            System.out.println("Sending alert to follower: " + follower.getName());
            User followerUser = userRepo.findById(follower.getId())
                .orElseThrow(() -> new UserNotFoundException(follower.getId()));
            // Here you would implement the logic to associate the alert with the follower
            followerUser.getAlerts().add(alert);
            
            // Notify the follower observer
            alertSubject.notifyObservers(new AlertDto(alert));
            
            // Detach after notification (optional)
            alertSubject.detach(followerObserver);
        });
    }
    @Override
    public List<AlertDto> displayAlerts(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        List<Alert> alerts = alertRepo.findByUserOrderByCreatedAtDesc(user);
        return alerts.stream()
            .map(AlertDto::new)
            .collect(Collectors.toList());
    }
    @Override
    public int getAlertsCount(Long userId) {
        return alertRepo.countByUserId(userId);
    }
}
