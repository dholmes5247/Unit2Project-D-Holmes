package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.repository.LoginEventRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class LoginTrackerService {

    private final LoginEventRepository loginEventRepository;

    public LoginTrackerService(LoginEventRepository loginEventRepository) {
        this.loginEventRepository = loginEventRepository;
    }

    // Returns the most recent login date for a given user
    public LocalDate getLastActiveDate(int userId) {
        List<LocalDateTime> timestamps = loginEventRepository.findLoginTimestamps(userId);

        // Convert timestamps to dates, remove duplicates, and sort in descending order
        List<LocalDate> loginDates = timestamps.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        // Return the most recent login date
        return loginDates.getFirst();
    }

    // Returns the number of consecutive days the user has logged in, ending today
    public int getLoginStreakDays(int userId) {
        List<LocalDateTime> timestamps = loginEventRepository.findLoginTimestamps(userId);

        // Convert timestamps to dates, remove duplicates, and sort in descending order
        List<LocalDate> loginDates = timestamps.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        if (loginDates.isEmpty()) return 0; // No logins recorded

        int streak = 1;
        LocalDate today = LocalDate.now();

        // Loop through sorted dates to count consecutive days
        for (int i = 0; i < loginDates.size() - 1; i++) {
            LocalDate current = loginDates.get(i);
            LocalDate next = loginDates.get(i + 1);

            if (current.minusDays(1).equals(next)) {
                streak++;
            } else {
                break; // Streak broken
            }
        }

        // Ensure the streak starts today; if not, reset streak to 0
        LocalDate firstLogin = loginDates.getFirst();
        if (firstLogin == null || !firstLogin.equals(today)) {
            streak = 0;
        }

        return streak;
    }
}




