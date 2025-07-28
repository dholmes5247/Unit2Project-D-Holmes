package com.example.Unit_2_Project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class LoginEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // switched from Long to int

    private int userId;

    private LocalDate loginDate;

    // Optional: Source info or login type?
    // private String loginMethod;

    // Getters & Setters with Lombok
}

