package com.example.Unit_2_Project.model;

@Entity
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL)
    private List<QuizAttemptQuestion> quizAttemptQuestions;
}

