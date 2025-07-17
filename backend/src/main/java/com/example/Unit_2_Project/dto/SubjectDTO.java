package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class SubjectDTO {
    private int id;
    private String name;
    private String description;
    private int questionCount;
    private String imageUrl;
}