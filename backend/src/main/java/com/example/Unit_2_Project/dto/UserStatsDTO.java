package com.example.Unit_2_Project.dto;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserStatsDTO {

    private int userId;

    // 🧮 Engagement Metrics
    private int totalQuizzesTaken;
    private int totalQuestionsAnswered;
    private double averageScore;
    private int currentStreakDays;

    // ⌛ Time-Based Metrics
    private LocalDate lastActiveDate;
    private int quizzesTakenThisWeek;

    // 🧠 Learning Metrics (future calc-based stats)
    private int categoriesExplored;
    private double improvementRate; // % difference over time

    // 🏆 Progress & Achievements
    // private int level;
    // private List<String> earnedBadges;

    // Optional: UI-friendly labels or tags
    private String summaryMessage;

    // Getters & Setters with Lombok
}

