package com.example.Unit_2_Project.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizAttemptDTO {

    @PrePersist
    protected void onCreate() {
        this.startedAt = LocalDateTime.now();
    } // Automatically sets the startedAt timestamp when a new attempt is created

    @NotNull(message = "Score must be provided")
    private Integer score;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Subject ID is required")
    private Integer subjectId;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}

//DTO assumes the user ID and subject ID from the frontend instead of full objects.
// expand it later to include timestamps, etc.