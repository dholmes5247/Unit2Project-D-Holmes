package com.example.Unit_2_Project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginStatsDTO {
    private LocalDate lastActiveDate;
    private int loginStreak;

    public LoginStatsDTO(LocalDate lastActiveDate, int loginStreak) {
        this.lastActiveDate = lastActiveDate;
        this.loginStreak = loginStreak;
    }
// constructors, getters, setters
}

