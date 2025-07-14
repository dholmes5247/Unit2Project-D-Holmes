package com.example.Unit_2_Project.model;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;
    private boolean answer; // true or false

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuizAttemptQuestion> quizAttemptQuestions;
}

