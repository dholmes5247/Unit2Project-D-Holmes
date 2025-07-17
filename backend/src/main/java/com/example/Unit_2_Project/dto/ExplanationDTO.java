package com.example.Unit_2_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExplanationDTO {
    private Integer id;
    private String question;
    private String explanation;
}

