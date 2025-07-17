package com.example.Unit_2_Project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String email;
    private String username;
}