package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.dto.UserStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

/**
 * Service class for aggregating user engagement statistics.
 * Pulls data from QuizHistory, LoginTracker, and ScoreAnalytics services,
 * then maps to a UserStatsDTO for frontend consumption.
 */
@Service
public class UserStatsService {

    // 📊 Tracks quiz-related stats: quizzes taken, questions answered, categories explored
    private final QuizHistoryService quizHistoryService;

    // 🔐 Tracks login activity and streaks
    private final LoginTrackerService loginTrackerService;

    // 📈 Tracks scoring and improvement over time
    private final ScoreAnalyticsService scoreAnalyticsService;

    /**
     * Constructor injection for dependency clarity and testing flexibility.
     */
    @Autowired
    public UserStatsService(
            QuizHistoryService quizHistoryService,
            LoginTrackerService loginTrackerService,
            ScoreAnalyticsService scoreAnalyticsService
    ) {
        this.quizHistoryService = quizHistoryService;
        this.loginTrackerService = loginTrackerService;
        this.scoreAnalyticsService = scoreAnalyticsService;
    }

    /**
     * Aggregates all user stats into a single DTO.
     * This is the source of truth for homepage stat panel and leaderboard-style components.
     *
     * @param userId The ID of the user whose stats are being retrieved.
     * @return A fully populated UserStatsDTO.
     */
    public UserStatsDTO getStatsForUser(int userId) {
        UserStatsDTO dto = new UserStatsDTO();
        dto.setUserId(userId);

        // 🧠 Quiz stats
        dto.setTotalQuizzesTaken(quizHistoryService.countQuizzesTaken(userId));
        dto.setTotalQuestionsAnswered(quizHistoryService.countQuestionsAnswered(userId));
        dto.setQuizzesTakenThisWeek(quizHistoryService.countQuizzesThisWeek(userId));
        dto.setCategoriesExplored(quizHistoryService.countCategoriesExplored(userId));

        // 🔐 Login streak and activity
        dto.setCurrentStreakDays(loginTrackerService.getLoginStreakDays(userId));
        dto.setLastActiveDate(loginTrackerService.getLastActiveDate(userId));

        // 📈 Score analytics
        dto.setAverageScore(scoreAnalyticsService.getAverageScore(userId));
        dto.setImprovementRate(scoreAnalyticsService.getImprovementRate(userId));

        // 🎙️ Summary message — customize this to match your app’s tone
        dto.setSummaryMessage("Nice work! You've answered " + dto.getTotalQuestionsAnswered() + " questions.");

        return dto;
    }
}
