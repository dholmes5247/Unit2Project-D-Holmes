package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class QuizAttemptSummaryDTO {

    private int id; // Quiz attempt ID

    private String subjectName; // Human-readable name of the subject

    private int score; // Final score

    private long timeTakenInSeconds; // Time taken to complete quiz
}
