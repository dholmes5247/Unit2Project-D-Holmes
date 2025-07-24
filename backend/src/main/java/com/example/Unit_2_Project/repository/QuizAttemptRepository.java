package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.dto.LeaderBoardEntryDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Integer> {
    // 🌟 Featured Leaderboard Query: Filter by Subject
    @Query("""
    SELECT new com.example.Unit_2_Project.dto.LeaderBoardEntryDTO(
        u.username,
        s.name,
        a.score,
        a.totalQuestions,
        a.duration,
        a.completedAt
    )
    FROM QuizAttempt a
    JOIN a.user u
    JOIN a.subject s
    WHERE s.id = :subjectId
    ORDER BY a.score DESC, a.duration ASC
""")
    List<LeaderBoardEntryDTO> findLeaderboardBySubject(@Param("subjectId") Integer subjectId);

    // Sort by score, then duration (actual entity field)
    List<QuizAttempt> findTop20ByOrderByScoreDescDurationAsc();

    // Subject-specific variant, optional for later filters
    List<QuizAttempt> findBySubjectIdOrderByScoreDescDurationAsc(Integer subjectId);

    // Still valid for user history
    List<QuizAttempt> findByUserId(int userId);

    List<QuizAttempt> findByUserIdOrderByScoreDescDurationAsc(Integer userId);

    // List<QuizAttempt> findAllByOrderByScoreDescDurationAsc();
    Page<QuizAttempt> findAllByOrderByScoreDescDurationAsc(Pageable pageable);
    Page<QuizAttempt> findByUserIdOrderByScoreDescDurationAsc(Integer userId, Pageable pageable);

}


