
package com.example.Unit_2_Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {

    private int id; // Unique ID of the user
    private String username; // The user's chosen username (display name)
    private String email; // The user's email address (used for account)
    private String school; // The school or institution the user is associated with
    // A list of quiz attempts made by the user — useful for showing quiz history or stats
    private List<QuizAttemptSummaryDTO> quizAttempts;

    // You can also add calculated stats here later (optional), like:
    // private int totalAttempts;
    // private double averageScore;
    // private int totalTimeSpent;
}
