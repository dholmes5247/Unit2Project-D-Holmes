package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity                              // JPA will map this class to a "subject" table
@Data                                // Lombok generates getters, setters, toString, equals, hashCode
@NoArgsConstructor                   // Required by JPA
@AllArgsConstructor                  // Useful for full construction

public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;                  // Primary key for Subject table

    @Column(nullable = false, unique = true)
    private String name;            // Subject name, e.g., "Java", must be unique and not null
    private String description;     // Description of the subject, e.g., "Learn Java basics"
    private String imageUrl;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude               // Prevent infinite loops when printing
    @EqualsAndHashCode.Exclude     // Prevents circular reference errors
    private List<Question> questions = new ArrayList<>();
    // List of questions under this subject

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<QuizAttempt> quizAttempts = new ArrayList<>();
    // List of quiz attempts taken under this subject

    /*Convenience method to maintain relationship with Question*/
    public void addQuestion(Question question) {
        questions.add(question);
        question.setSubject(this);
    }

    /**
     * Convenience method to maintain bidirectional relationship with QuizAttempt
     */
    public void addQuizAttempt(QuizAttempt attempt) {
        quizAttempts.add(attempt);
        attempt.setSubject(this);
    }
}


