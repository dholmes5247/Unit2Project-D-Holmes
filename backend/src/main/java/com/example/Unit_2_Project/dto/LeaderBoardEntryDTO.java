package com.example.Unit_2_Project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderBoardEntryDTO {
    private String username;
    private String subjectName;
    private Integer score;
    private Integer totalQuestions;
    private Long duration;  // duration in seconds
    private LocalDateTime attemptDate;


}

