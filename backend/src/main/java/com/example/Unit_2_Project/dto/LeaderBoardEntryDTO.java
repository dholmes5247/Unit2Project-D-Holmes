package com.example.Unit_2_Project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoardEntryDTO {
    private String username;  // Username of the user
    private String subjectName; // Name of the subject
    private Integer score;        // Score of the user
    private Integer totalQuestions;
    private Long timeTakenInSeconds; // Time taken by the user in seconds
    private LocalDateTime attemptDate; // Date and time of the attempt

}
