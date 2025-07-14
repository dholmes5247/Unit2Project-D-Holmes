package com.example.Unit_2_Project.model;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<QuizAttempt> quizAttempts;
}

