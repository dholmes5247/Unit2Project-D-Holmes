package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data                                // Lombok: generates getters/setters, equals/hashCode, toString
@NoArgsConstructor                   // Required by JPA
@AllArgsConstructor
public class QuizAttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                  // Primary key for this record

    private boolean userAnswer;     // The user's answer (true/false)
    private boolean correct;        // Whether the answer was correct

    @ManyToOne
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    private QuizAttempt quizAttempt;   // The quiz attempt this question belongs to

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;         // The actual question being answered
}

