package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String name;
    private String email;
    private String school;
    private String password; // optional — only populated if user changes it
}

