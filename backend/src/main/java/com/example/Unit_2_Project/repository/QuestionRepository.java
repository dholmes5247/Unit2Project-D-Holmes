package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // Additional queries added if needed
    List<Question> findBySubject_Id(int id);

}

