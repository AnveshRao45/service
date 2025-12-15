package com.failfeed.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.failfeed.service.dto.AlertDto;

import com.failfeed.service.service.ManageAlertsServiceInterface;



@RestController
@RequestMapping("/alerts")
public class ManageAlertsController {   
    // Controller methods for managing alerts would go here
    private final ManageAlertsServiceInterface manageAlertsService;

    public ManageAlertsController(ManageAlertsServiceInterface manageAlertsService) {
        this.manageAlertsService = manageAlertsService;
    }
    
    // @PostMapping("/createAlert/{userId}")
    public void createPostAlert(@PathVariable Long userId) {
        manageAlertsService.createPostAlert(userId);
    }

    public void createFollowAlert(@PathVariable Long followerId, @PathVariable Long followedId) {
        manageAlertsService.createFollowAlert(followerId, followedId);
    }

    public void createRetweetAlert(@PathVariable Long retweeterId, @PathVariable Long originalPosterId) {
        manageAlertsService.createRetweetAlert(retweeterId, originalPosterId);
    }

    public void createLikeAlert(@PathVariable Long userId, @PathVariable Long originalPosterId) {
        manageAlertsService.createRetweetAlert(userId, originalPosterId);
    }
    
    @GetMapping("/getAlertById/{alertId}")
    public AlertDto getAlertById(@PathVariable Long alertId) {
        return manageAlertsService.getAlertById(alertId);
    }

    @GetMapping("/displayAlerts/{userId}")
    public List<AlertDto> displayAlerts(@PathVariable Long userId) {
        return manageAlertsService.displayAlerts(userId);
    }
    @GetMapping("/getAlertsCount/{userId}")
    public int getAlertsCount(@PathVariable Long userId) {
        return manageAlertsService.getAlertsCount(userId);
    }

}
