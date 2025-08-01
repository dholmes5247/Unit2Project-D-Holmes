package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.model.QuizAttemptQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizAttemptQuestionRepository extends JpaRepository<QuizAttemptQuestion, Integer> {
    // Additional queries can be added here if needed
    // For example, to find all attempts for a specific user:

}