package com.example.Unit_2_Project.dto;

import com.example.Unit_2_Project.model.Question.Difficulty;
import lombok.Data;

@Data
public class QuestionDTO {
    private String text;
    private boolean answer;
    private Difficulty difficulty;
    private int subjectId;  // Link to existing Subject by ID
}