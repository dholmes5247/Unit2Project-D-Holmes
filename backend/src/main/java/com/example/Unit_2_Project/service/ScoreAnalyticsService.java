package com.example.Unit_2_Project.service;

import org.springframework.stereotype.Service;

@Service
public class ScoreAnalyticsService {

    public double getAverageScore(int userId) {
        // TODO: Calculate from quiz scores
        return 87.5;
    }

    public double getImprovementRate(int userId) {
        // TODO: Compare recent scores vs historical
        return 12.3;
    }
}
