package com.example.Unit_2_Project.model;

import jakarta.persistence.*;      // JPA annotations for entity mapping
import lombok.*;                  // Lombok annotations to reduce boilerplate

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity                           // Marks this class as a JPA entity (table)
@Getter                          // Lombok: generates getters for all fields
@Setter                          // Lombok: generates setters for all fields
@NoArgsConstructor               // Lombok: no-arg constructor (required by JPA)
@RequiredArgsConstructor         // Lombok: constructor for @NonNull fields

public class User {

    //  Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;              // Primary key, auto-incremented

    @NonNull
    @Column(nullable = false, unique = true)
    private String username;     // Unique and required username

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude            // Avoids recursion in toString()
    @EqualsAndHashCode.Exclude  // Prevents infinite loops in equals/hashCode
    private List<QuizAttempt> quizAttempts = new ArrayList<>();
    // One user can have many quiz attempts

    // Convenience Method
    // Adds a QuizAttempt and keeps the bidirectional relationship in sync

    public void addQuizAttempt(QuizAttempt attempt) {
        quizAttempts.add(attempt);
        attempt.setUser(this);
    }

    // ---------- equals & hashCode (based on ID only) ----------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}



