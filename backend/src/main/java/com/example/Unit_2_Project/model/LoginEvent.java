package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class LoginEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // switched from Long to int
    // Using int for simplicity, assuming no more than 2 billion login events

    private LocalDateTime timestamp;

    @Column(name = "login_date", nullable = false)
    private LocalDate loginDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Optional: Source info or login type?
    // private String loginMethod;

    // Getters & Setters with Lombok @Data
}

