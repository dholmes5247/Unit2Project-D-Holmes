package com.example.Unit_2_Project.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizAttemptDTO {
    private int id;
    private int score;
    private Long timeTakenInSeconds;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Integer totalQuestions; // total number of questions in the quiz
    // Directly reflect duration in seconds
    private Long duration;
    private UserDTO user; // include full user info
    private SubjectDTO subject;
}


//DTO assumes the user ID and subject ID from the frontend instead of full objects.
// expand it later to include timestamps, etc.