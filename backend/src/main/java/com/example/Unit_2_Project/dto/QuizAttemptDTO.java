package com.example.Unit_2_Project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizAttemptDTO {

    @NotNull(message = "Score must be provided")
    private Integer score;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Subject ID is required")
    private Integer subjectId;
}

//DTO assumes the user ID and subject ID from the frontend instead of full objects.
// expand it later to include timestamps, etc.