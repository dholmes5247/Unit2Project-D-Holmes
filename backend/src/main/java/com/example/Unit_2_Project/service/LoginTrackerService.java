package com.example.Unit_2_Project.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class LoginTrackerService {

    public int getLoginStreakDays(int userId) {
        // TODO: Replace with real streak logic based on login dates
        return 4;
    }

    public LocalDate getLastActiveDate(int userId) {
        // TODO: Query latest login or activity timestamp
        return LocalDate.now().minusDays(1);
    }
}
