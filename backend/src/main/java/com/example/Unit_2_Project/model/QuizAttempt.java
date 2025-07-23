package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {

    // Custom constructor without ID (JPA will generate the ID automatically)
    public QuizAttempt(User user, Subject subject, int score) {
        this.user = user;
        this.subject = subject;
        this.score = score;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int score;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @Column(nullable = true)
    private Long duration;

    // calculating seconds for time taken
    public long getTimeTakenInSeconds() {
        if (this.startedAt != null && this.completedAt != null) {
            return java.time.Duration.between(this.startedAt, this.completedAt).getSeconds();
        }
        return 0;
    }
    @Column(name = "total_questions", nullable = false)
    private Integer totalQuestions;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private Subject subject;

    @OneToMany(mappedBy = "quizAttempt", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<QuizAttemptQuestion> quizAttemptQuestions = new ArrayList<>();



    // Convenience method to keep both sides of the relationship in sync
    public void addAttemptQuestion(QuizAttemptQuestion attemptQuestion) {
        quizAttemptQuestions.add(attemptQuestion);
        attemptQuestion.setQuizAttempt(this);
    }


}




