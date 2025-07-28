package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.LoginStatsDTO;
import com.example.Unit_2_Project.service.LoginTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class LoginStatsController {

    private final LoginTrackerService loginTrackerService;

    public LoginStatsController(LoginTrackerService loginTrackerService) {
        this.loginTrackerService = loginTrackerService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<LoginStatsDTO> getLoginStats(@PathVariable int userId) {
        LoginStatsDTO stats = new LoginStatsDTO(
                loginTrackerService.getLastActiveDate(userId),
                loginTrackerService.getLoginStreakDays(userId)
        );
        return ResponseEntity.ok(stats);
    }
}

