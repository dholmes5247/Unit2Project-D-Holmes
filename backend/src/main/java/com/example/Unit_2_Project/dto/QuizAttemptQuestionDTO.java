package com.example.Unit_2_Project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAttemptQuestionDTO {
    private Integer id;

    @NotNull(message = "Answer is required")
    private boolean answer;  // User's answer (true or false)

    @NotNull(message = "Correctness of the answer is required")
    private boolean correct;  // Whether the answer is correct

    @NotNull(message = "Question ID is required")
    private Integer questionId;  // ID of the Question linked to this QuizAttemptQuestion

    @NotNull(message = "QuizAttempt ID is required")
    private Integer quizAttemptId;  // ID of the QuizAttempt linked to this QuizAttemptQuestion
}


