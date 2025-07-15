package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data                                // Lombok: Getters, Setters, toString, equals, hashCode
@NoArgsConstructor                   // JPA requires a no-arg constructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                  // Primary key

    private String text;            // The question itself (e.g., "Java is statically typed?")
    private boolean answer;         // true or false (correct answer)

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;        // The subject this question belongs to

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude               // Prevent recursive printing
    @EqualsAndHashCode.Exclude
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();
    // Links to user responses in quiz attempts

    // Optional convenience method to keep both sides of the relationship in sync
    // when adding a QuizAttemptQuestion
    public void addQuizAttemptQuestion(QuizAttemptQuestion q) {
        quizAttemptQuestions.add(q);
        q.setQuestion(this);
    }
}


