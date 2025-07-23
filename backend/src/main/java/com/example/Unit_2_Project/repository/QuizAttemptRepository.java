package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {

    // Now valid: Sort by score, then duration (actual entity field)
    List<QuizAttempt> findTop20ByOrderByScoreDescDurationAsc();

    // Subject-specific variant, optional for later filters
    List<QuizAttempt> findBySubjectIdOrderByScoreDescDurationAsc(Integer subjectId);

    // Still valid for user history
    List<QuizAttempt> findByUserId(int userId);
}


