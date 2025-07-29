package com.example.Unit_2_Project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data

public class GeminiRequestDTO {
    @NotBlank(message = "Question cannot be blank to call Gemini API")
    private String question;
    @NotBlank(message = "Context cannot be blank to call Gemini API")
    private String context;

    public GeminiRequestDTO() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}

