package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class QuizHistoryService {

    private final QuizAttemptRepository quizAttemptRepository;

    public QuizHistoryService(QuizAttemptRepository quizAttemptRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
    }

    // Counts all quizzes submitted by the user
    public int countQuizzesTaken(int userId) {
        return quizAttemptRepository.countByUserId(userId);
    }

    // Totals the number of individual questions answered across all quizzes
    public int countQuestionsAnswered(int userId) {
        return quizAttemptRepository.countTotalQuestionsAnswered(userId) != null
                ? quizAttemptRepository.countTotalQuestionsAnswered(userId)
                : 0;
    }

    // Retrieves how many quizzes the user completed starting Monday of this week
    public int countQuizzesThisWeek(int userId) {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        return quizAttemptRepository.countQuizzesThisWeek(userId, startOfWeek);
    }

    // Returns how many unique quiz categories the user has explored
    public int countCategoriesExplored(int userId) {
        return quizAttemptRepository.countDistinctCategoriesExplored(userId);
    }
}



