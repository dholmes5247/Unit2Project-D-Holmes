package com.example.Unit_2_Project.service;

import org.springframework.stereotype.Service;

@Service
public class QuizHistoryService {

    public int countQuizzesTaken(int userId) {
        // TODO: Replace with actual query
        return 5;
    }

    public int countQuestionsAnswered(int userId) {
        // TODO: Replace with actual query
        return 42;
    }

    public int countQuizzesThisWeek(int userId) {
        // TODO: Filter by timestamp or week logic
        return 2;
    }

    public int countCategoriesExplored(int userId) {
        // TODO: Track distinct subjects or tags
        return 3;
    }
}

