package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.LeaderBoardEntryDTO;


import com.example.Unit_2_Project.dto.QuizAttemptDTO;
import com.example.Unit_2_Project.dto.SubjectDTO;
import com.example.Unit_2_Project.dto.UserDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderBoardController {

    private final QuizAttemptRepository quizAttemptRepository;

    // GET /leaderboard?subjectId=1
    @GetMapping
    public ResponseEntity<List<LeaderBoardEntryDTO>> getLeaderboardBySubject(@RequestParam Integer subjectId) {
        List<LeaderBoardEntryDTO> leaderboard = quizAttemptRepository.findLeaderboardBySubject(subjectId);
        return ResponseEntity.ok(leaderboard);
    }

    // NEW: GET top attempts by subject (Leaderboard-style)
    @GetMapping("/top")
    public ResponseEntity<List<QuizAttemptDTO>> getTopAttempts(@RequestParam(required = false) Integer subjectId) {
        List<QuizAttempt> attempts;

        // Conditional fetch based on subjectId
        if (subjectId != null) {
            attempts = quizAttemptRepository.findBySubjectIdOrderByScoreDescDurationAsc(subjectId);
        } else {
            attempts = quizAttemptRepository.findTop20ByOrderByScoreDescDurationAsc();
        }

        // Manual DTO mapping with embedded user + subject info
        List<QuizAttemptDTO> dtoList = attempts.stream()
                .map(attempt -> {
                    QuizAttemptDTO dto = new QuizAttemptDTO();
                    dto.setId(attempt.getId());
                    dto.setScore(attempt.getScore());
                    dto.setDuration(attempt.getDuration() != null ? attempt.getDuration() : 0); // or use timeTakenInSeconds
                    dto.setTimeTakenInSeconds(dto.getDuration());
                    dto.setStartedAt(attempt.getStartedAt());
                    dto.setCompletedAt(attempt.getCompletedAt());

                    if (attempt.getUser() != null) {
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(attempt.getUser().getId());
                        userDTO.setUsername(attempt.getUser().getUsername());
                        userDTO.setSchool(attempt.getUser().getSchool());
                        dto.setUser(userDTO);
                    }

                    if (attempt.getSubject() != null) {
                        SubjectDTO subjectDTO = new SubjectDTO();
                        subjectDTO.setId(attempt.getSubject().getId());
                        subjectDTO.setName(attempt.getSubject().getName());
                        dto.setSubject(subjectDTO);
                    }

                    return dto;
                })
                .limit(20)
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}

