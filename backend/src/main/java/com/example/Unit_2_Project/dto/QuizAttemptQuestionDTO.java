package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class QuizAttemptQuestionDTO {
    private boolean answer;       // The user's answer to the question (true/false)
    private int questionId;       // ID of the question
    private int quizAttemptId;    // ID of the quiz attempt
}
