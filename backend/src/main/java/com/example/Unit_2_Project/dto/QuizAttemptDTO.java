package com.example.Unit_2_Project.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizAttemptDTO {
// DTO for quiz attempt data transfer object
    private Integer score;
    private Integer userId;
    private Integer subjectId;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private Long duration;
    private String school;
}

//DTO assumes the user ID and subject ID from the frontend instead of full objects.
// expand it later to include timestamps, etc.