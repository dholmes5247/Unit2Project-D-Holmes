package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreAnalyticsService {

    private final QuizAttemptRepository quizAttemptRepository;

    public ScoreAnalyticsService(QuizAttemptRepository quizAttemptRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
    }

    // Calculates average score across all quiz attempts
    public double getAverageScore(int userId) {
        Double average = quizAttemptRepository.findAverageScore(userId);
        return average != null ? average : 0.0;
    }

    // Approximates improvement by comparing earliest and latest scores
    // (You can later refine this with a moving average or regression)
    public double getImprovementRate(int userId) {
        List<Double> scores = quizAttemptRepository.findAllScoresByUserIdOrdered(userId);
        if (scores.size() < 2) return 0.0;

        double recent = scores.get(0);
        double past = scores.get(scores.size() - 1);
        double delta = recent - past;

        return (delta / Math.max(past, 1.0)) * 100.0;
    }
}


