package com.failfeed.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.failfeed.service.dto.AlertDto;
import com.failfeed.service.dto.UserDto;
import com.failfeed.service.exception.AlertNotFoundException;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.model.Alert;
import com.failfeed.service.model.User;
import com.failfeed.service.observer.AlertSubject;
import com.failfeed.service.observer.NotificationObserver;
import com.failfeed.service.repository.AlertRepository;
import com.failfeed.service.repository.UserRepository;
import com.failfeed.service.state.AlertContext;


@Service
public class ManageAlertsServiceImpl implements ManageAlertsServiceInterface {

    private final UserRepository userRepo;
    private final AlertRepository alertRepo;
    private final AlertSubject alertSubject;

    public ManageAlertsServiceImpl(UserRepository userRepository, AlertRepository alertRepository) {
        this.userRepo = userRepository;
        this.alertRepo = alertRepository;
        this.alertSubject = new AlertSubject(this.alertRepo);
    }

    @Override
    public void createFollowAlert(Long followerId, Long followedId) {
        User follower = userRepo.findById(followerId)
            .orElseThrow(() -> new UserNotFoundException(followerId));
        User followed = userRepo.findById(followedId)
            .orElseThrow(() -> new UserNotFoundException(followedId));

        //Initialize alert state
        AlertContext alertContext = new AlertContext();
        alertContext.send();
        //create and attach notification observer
        NotificationObserver notificationObserver = new NotificationObserver(followed);
        alertSubject.attach(notificationObserver);
        //send notification/alerts to the observers
        String alertContent = follower.getName() + " started following " + followed.getName();
        alertSubject.notifyObservers(alertContent);
        alertSubject.detach(notificationObserver);
        alertContext.deliver();
    }

    @Override
    public void  createPostAlert(Long userId) {

        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        String alertContent = user.getName() + " has posted a Badge!";
        List<UserDto> followers = user.getFollowers().stream()
                                    .map(UserDto::new)
                                .collect(Collectors.toList());
                                
        followers.forEach(followerDto -> {
            User follower = userRepo.findById(followerDto.getId())
                .orElseThrow(() -> new UserNotFoundException(followerDto.getId())); 
            //Initialize alert state
            AlertContext alertContext = new AlertContext();
            alertContext.send();
            //create and attach notification observer
            NotificationObserver notificationObserver = new NotificationObserver(follower);
            alertSubject.attach(notificationObserver);            
            // Simulate sending alert
            System.out.println("Sending alert to follower: " + follower.getName());                        
            // Notify the follower observer
            alertSubject.notifyObservers(alertContent);            
            // Detach after notification (optional)
            alertSubject.detach(notificationObserver);
            alertContext.deliver();
        });

    }

    @Override
    public void createRetweetAlert(Long retweeterId, Long originalPosterId) {
        User retweeter = userRepo.findById(retweeterId)
            .orElseThrow(() -> new UserNotFoundException(retweeterId));
        User originalPoster = userRepo.findById(originalPosterId)
            .orElseThrow(() -> new UserNotFoundException(originalPosterId));
        String alertContent = retweeter.getName() + " rebadged " + originalPoster.getName() + "'s badge";
        List<NotificationObserver> notificationObservers = new ArrayList<>();
        notificationObservers.add(new NotificationObserver(originalPoster));
        List<UserDto> retweeterFollowers = retweeter.getFollowers().stream()
                                    .map(UserDto::new)
                                .collect(Collectors.toList());
        retweeterFollowers.forEach(retweetFollowerDto -> {
            User follower = userRepo.findById(retweetFollowerDto.getId())
                .orElseThrow(() -> new UserNotFoundException(retweetFollowerDto.getId()));
            notificationObservers.add(new NotificationObserver(follower));
        });
        notificationObservers.forEach(observer -> {
            // Initialize alert state
            AlertContext alertContext = new AlertContext();
            alertContext.send();
            alertSubject.attach(observer);            
            // Simulate sending alert
            System.out.println("Sending alert to observer : " + observer.getObserver().getName());                        
            // Notify the follower observer
            alertSubject.notifyObservers(alertContent);            
            // Detach after notification (optional)
            alertSubject.detach(observer);
            alertContext.deliver();
        });
        
        
    }
    
    @Override
    public void createLikeAlert(Long userId, Long originalPosterId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        User originalPoster = userRepo.findById(originalPosterId)
            .orElseThrow(() -> new UserNotFoundException(originalPosterId));

        //Initialize alert state
        AlertContext alertContext = new AlertContext();
        alertContext.send();
        //create and attach notification observer
        NotificationObserver notificationObserver = new NotificationObserver(originalPoster);
        alertSubject.attach(notificationObserver);
        //send notification/alerts to the observers
        String alertContent = user.getName() + " liked " + originalPoster.getName() + "'s badge";
        alertSubject.notifyObservers(alertContent);
        alertSubject.detach(notificationObserver);
        alertContext.deliver();
    }
    

    @Override
    public AlertDto getAlertById(Long alertId) {
        // Implementation here
        Alert alert = alertRepo.findById(alertId)
            .orElseThrow(() -> new AlertNotFoundException(alertId));
        return new AlertDto(alert);
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
