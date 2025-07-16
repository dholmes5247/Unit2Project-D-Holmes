package com.example.Unit_2_Project.dto;

import com.example.Unit_2_Project.model.Question.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionDTO {
    @NotBlank(message = "Question text cannot be empty")
    private String text;

    @NotNull(message = "Answer must be true or false")
    private boolean answer;

    @NotNull(message = "Difficulty must be provided")
    private Difficulty difficulty;

    @NotNull(message = "Subject ID must be provided")
    private Integer subjectId;
}
