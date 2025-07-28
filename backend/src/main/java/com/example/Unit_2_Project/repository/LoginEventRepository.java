package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Integer> {

    // Gets all login events for a user, ordered descending by login time
    List<LoginEvent> findByUser_IdOrderByTimestampDesc(int userId); // Valid Spring syntax


    // Finds login events that occurred on a specific date
    List<LoginEvent> findByUser_IdAndLoginDate(int userId, LocalDate loginDate);

    // Counts total login events for user (optional for overall engagement metrics)
    int countByUser_IdAndTimestampBetween(int userId, LocalDateTime start, LocalDateTime end); // ✅
// only valid if 'loginDate' is a mapped field


    // Finds distinct login dates to help calculate streaks
    @Query(value = "SELECT DISTINCT DATE(timestamp) FROM login_event WHERE user_id = :userId ORDER BY DATE(timestamp)", nativeQuery = true)
    List<LocalDate> findDistinctLoginDates(@Param("userId") int userId);

    @Query("SELECT le.timestamp FROM LoginEvent le WHERE le.user.id = :userId ORDER BY le.timestamp DESC")
    List<LocalDateTime> findLoginTimestamps(@Param("userId") int userId);



}

