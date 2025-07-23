package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class QuizAttemptSummaryDTO {
    private Integer id;
    private String subjectName;
    private Integer score;
    private Long timeTakenInSeconds;
    private Integer totalQuestions;

}

