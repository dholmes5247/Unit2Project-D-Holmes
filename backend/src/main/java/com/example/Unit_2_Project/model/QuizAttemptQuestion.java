package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor  // Lombok generates the default no-argument constructor
@AllArgsConstructor
public class QuizAttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)  // Ensure it's not nullable to avoid errors when inserting
    private boolean answer;  // The user's answer to the question (true/false)

    @Column(nullable = false)  // Ensure it's not nullable
    private boolean correct;  // Whether the answer is correct or not

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    @JsonBackReference
    private QuizAttempt quizAttempt;  // Link to QuizAttempt

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference
    private Question question;  // Link to Question

    // Convenience method to set the QuizAttempt and Question
    public void setQuizAttemptAndQuestion(QuizAttempt quizAttempt, Question question) {
        this.quizAttempt = quizAttempt;
        this.question = question;
    }

    // Convenience method to set the answer and correctness
    public void setAnswerAndCorrectness(boolean answer, boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    // Convenience method to clear relationships when needed (good for cascading)
    public void clearRelationships() {
        this.quizAttempt = null;
        this.question = null;
    }
}





