package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.repository.LoginEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoginTrackerService {

    private final LoginEventRepository loginEventRepository;

    public LoginTrackerService(LoginEventRepository loginEventRepository) {
        this.loginEventRepository = loginEventRepository;
    }

    public LocalDate getLastActiveDate(int userId) {
        List<LocalDate> logins = loginEventRepository.findDistinctLoginDates(userId);
        return logins.isEmpty() ? null : logins.get(0);
    }

    public int getLoginStreakDays(int userId) {
        List<LocalDate> loginDates = loginEventRepository.findDistinctLoginDates(userId);
        if (loginDates.isEmpty()) return 0;

        int streak = 1;
        LocalDate today = LocalDate.now();

        for (int i = 0; i < loginDates.size() - 1; i++) {
            LocalDate current = loginDates.get(i);
            LocalDate next = loginDates.get(i + 1);

            // If the gap is exactly 1 day, continue the streak
            if (current.minusDays(1).equals(next)) {
                streak++;
            } else {
                break; // streak broken
            }
        }

        // Bonus: Only count streak if user logged in today
        if (!loginDates.get(0).equals(today)) {
            streak = 0;
        }

        return streak;
    }

}


