package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String text;
    private boolean answer;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private Subject subject;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();

    public void addQuizAttemptQuestion(QuizAttemptQuestion q) {
        quizAttemptQuestions.add(q);
        q.setQuestion(this);
    }
}



