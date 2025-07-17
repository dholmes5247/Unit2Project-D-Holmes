package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {

    // Custom query method to find QuizAttempts by User ID
    List<QuizAttempt> findByUserId(int userId);

}

