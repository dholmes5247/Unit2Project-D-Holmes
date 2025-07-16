package com.example.Unit_2_Project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAttemptQuestionDTO {

    @NotNull(message = "Answer is required")
    private boolean answer;

    @NotNull(message = "Question ID is required")
    private Integer questionId;

    @NotNull(message = "QuizAttempt ID is required")
    private Integer quizAttemptId;
}

