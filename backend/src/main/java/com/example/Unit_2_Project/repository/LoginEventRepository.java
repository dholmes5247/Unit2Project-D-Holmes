package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoginEventRepository extends JpaRepository<LoginEvent, Long> {

    // Gets all login events for a user, ordered descending by login time
    List<LoginEvent> findByUserIdOrderByLoginTimeDesc(int userId);

    // Finds login events that occurred on a specific date
    List<LoginEvent> findByUserIdAndLoginDate(int userId, LocalDate loginDate);

    // Counts total login events for user (optional for overall engagement metrics)
    long countByUserId(int userId);

    // Finds distinct login dates to help calculate streaks
    @Query("SELECT DISTINCT DATE(le.loginTime) FROM LoginEvent le WHERE le.userId = :userId ORDER BY DATE(le.loginTime) DESC")
    List<LocalDate> findDistinctLoginDates(int userId);
}

