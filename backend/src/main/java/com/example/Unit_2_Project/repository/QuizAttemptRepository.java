package com.example.Unit_2_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Unit_2_Project.model.QuizAttemptQuestion;
import com.example.Unit_2_Project.model.QuizAttempt;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {
    // Additional queries can be added here if needed
    // For example, to find all attempts for a specific user:
    // List<QuizAttempt> findByUserId(int userId);
}
