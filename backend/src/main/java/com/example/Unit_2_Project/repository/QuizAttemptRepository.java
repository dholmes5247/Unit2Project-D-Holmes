package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.dto.LeaderBoardEntryDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    // 🔢 Leaderboard and pagination support
    List<QuizAttempt> findTop20ByOrderByScoreDescDurationAsc();
    List<QuizAttempt> findBySubjectIdOrderByScoreDescDurationAsc(Integer subjectId);
    List<QuizAttempt> findByUserId(int userId);
    List<QuizAttempt> findByUserIdOrderByScoreDescDurationAsc(Integer userId);
    Page<QuizAttempt> findAllByOrderByScoreDescDurationAsc(Pageable pageable);
    Page<QuizAttempt> findByUserIdOrderByScoreDescDurationAsc(Integer userId, Pageable pageable);

    // 📊 Profile Stat Queries — for UserStatsService

    // Total quizzes taken by user
    int countByUserId(int userId);

    // Total questions answered (assuming each QuizAttempt has totalQuestions field)
    @Query("SELECT SUM(a.totalQuestions) FROM QuizAttempt a WHERE a.user.id = :userId")
    Integer countTotalQuestionsAnswered(@Param("userId") int userId);

    // Distinct categories explored (assuming 'subject' is your category)
    @Query("SELECT COUNT(DISTINCT a.subject.id) FROM QuizAttempt a WHERE a.user.id = :userId")
    int countDistinctCategoriesExplored(@Param("userId") int userId);

    // Quizzes taken this week
    @Query("SELECT COUNT(a) FROM QuizAttempt a WHERE a.user.id = :userId AND a.timestamp >= :startOfWeek")
    int countQuizzesThisWeek(@Param("userId") int userId, @Param("startOfWeek") LocalDate startOfWeek);

    // Average score for user
    @Query("SELECT AVG(a.score) FROM QuizAttempt a WHERE a.user.id = :userId")
    Double findAverageScore(@Param("userId") int userId);

    // All scores in descending order — for improvement rate calculation
    @Query("SELECT a.score FROM QuizAttempt a WHERE a.user.id = :userId ORDER BY a.timestamp DESC")
    List<Double> findAllScoresByUserIdOrdered(@Param("userId") int userId);
}



