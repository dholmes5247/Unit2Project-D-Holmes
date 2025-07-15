package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // Additional queries added if needed
}

