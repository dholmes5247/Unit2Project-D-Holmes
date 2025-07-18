package com.example.Unit_2_Project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotBlank(message = "School is required.")
    @Size(min = 3, max = 100, message = "School name must be between 3 and 100 characters.")
    @Column(nullable = false)
    private String school;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<QuizAttempt> quizAttempts = new ArrayList<>();

    public void addQuizAttempt(QuizAttempt attempt) {
        quizAttempts.add(attempt);
        attempt.setUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}






