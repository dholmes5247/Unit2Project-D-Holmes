package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean answer; // The user's answer to the question (true/false)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id")
    @JsonBackReference
    private QuizAttempt quizAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;
}


