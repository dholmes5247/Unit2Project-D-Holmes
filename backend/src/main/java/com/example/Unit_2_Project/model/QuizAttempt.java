package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity                          // Marks this class as a JPA entity (i.e., a database table)
@Data                            // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor               // Lombok: generates a no-arg constructor (needed by JPA)
@AllArgsConstructor              // Lombok: generates a constructor with all fields
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;              // Primary key, auto-generated

    private int score;          // Total score for this quiz attempt

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;          // The user who took this quiz

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;    // The subject this quiz attempt belongs to

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude           // Prevents infinite recursion in toString()
    @EqualsAndHashCode.Exclude // Excludes from equals/hashCode to avoid circular logic
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();
    // List of question responses in this quiz attempt

    /* Convenience method to keep both sides of the relationship in sync */
    public void addAttemptQuestion(QuizAttemptQuestion attemptQuestion) {
        quizAttemptQuestions.add(attemptQuestion);
        attemptQuestion.setQuizAttempt(this);
    }
}


