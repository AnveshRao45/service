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
    public AlertDto createPostAlert(@PathVariable Long userId) {
        return manageAlertsService.createPostAlert(userId);
    }

    public AlertDto createFollowAlert(@PathVariable Long followerId, @PathVariable Long followedId) {
        return manageAlertsService.createFollowAlert(followerId, followedId);
    }

    public AlertDto createRetweetAlert(@PathVariable Long retweeterId, @PathVariable Long originalPosterId) {
        return manageAlertsService.createRetweetAlert(retweeterId, originalPosterId);
    }
    
    @GetMapping("/getAlertById/{alertId}")
    public AlertDto getAlertById(@PathVariable Long alertId) {
        return manageAlertsService.getAlertById(alertId);
    }

    // @PostMapping("/sendAlertToFollowers/{alertId}/{userId}")
    public void sendAlertToFollowers(@PathVariable Long alertId, @PathVariable Long userId) {
        manageAlertsService.sendAlertToFollowers(alertId, userId);
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
