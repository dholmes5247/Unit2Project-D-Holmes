package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.UserStatsDTO;
import com.example.Unit_2_Project.service.UserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/stats")
@CrossOrigin(origins = "*") // Optional: helps during local dev if frontend is on a different port
public class UserStatsController {

    private final UserStatsService userStatsService;

    @Autowired
    public UserStatsController(UserStatsService userStatsService) {
        this.userStatsService = userStatsService;
    }

    /**
     * GET endpoint to return full user stats DTO.
     * Example: /api/user/stats/42
     *
     * @param userId ID of the user
     * @return UserStatsDTO containing engagement metrics
     */
    @GetMapping("/{userId}")
    public UserStatsDTO getStats(@PathVariable int userId) {
        return userStatsService.getStatsForUser(userId);
    }
}

