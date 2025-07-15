package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();

    // Convenience method to keep both sides of the relationship in sync
    public void addAttemptQuestion(QuizAttemptQuestion attemptQuestion) {
        quizAttemptQuestions.add(attemptQuestion);
        attemptQuestion.setQuizAttempt(this);
    }

    // Custom constructor without ID (JPA will generate the ID automatically)
    public QuizAttempt(User user, Subject subject, int score) {
        this.user = user;
        this.subject = subject;
        this.score = score;
    }
}




