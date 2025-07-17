
package com.example.Unit_2_Project.dto;

import lombok.Data;

@Data
public class UserSignupDTO {
    private String username;
    private String name;       // < Required
    private String email;
    private String password;
    private String school;
}