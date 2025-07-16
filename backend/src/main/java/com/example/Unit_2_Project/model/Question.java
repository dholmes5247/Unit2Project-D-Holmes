package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text; // The question itself (e.g., "Java is statically typed?")
    private boolean answer; // true or false (correct answer)

    @Enumerated(EnumType.STRING) // Stores enum name in the DB (not ordinal number)
    private Difficulty difficulty; // Difficulty level of the question (EASY, MEDIUM, HARD)

    public Question(String text, boolean answer, Difficulty difficulty, Subject subject) {
        this.text = text;
        this.answer = answer;
        this.difficulty = difficulty;
        this.subject = subject;
    }

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private Subject subject; // The subject this question belongs to

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // Prevents infinite recursion in JSON serialization
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();
    // Links to user responses in quiz attempts

    // Optional convenience method to keep both sides of the relationship in sync
    // when adding a QuizAttemptQuestion
    public void addQuizAttemptQuestion(QuizAttemptQuestion q) {
        quizAttemptQuestions.add(q);
        q.setQuestion(this);
    }


}




