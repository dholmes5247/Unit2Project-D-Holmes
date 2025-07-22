package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuizAttemptDTO;
import com.example.Unit_2_Project.dto.QuizAttemptSummaryDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import com.example.Unit_2_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/quiz-attempts")
@CrossOrigin(origins = "*") // Allow frontend (e.g. http://localhost:5173) to access API
public class QuizAttemptController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    // GET all quiz attempts
    @GetMapping
    public List<QuizAttempt> getAllQuizAttempts() {
        return quizAttemptRepository.findAll();
    }

    // GET single attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizAttempt> getQuizAttemptById(@PathVariable int id) {
        QuizAttempt attempt = quizAttemptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "QuizAttempt with ID " + id + " not found"
                ));
        return ResponseEntity.ok(attempt);
    }

    // ✅ NEW: GET top attempts by subject (Leaderboard-style)
    @GetMapping("/top")
    public ResponseEntity<List<QuizAttemptDTO>> getTopAttemptsBySubject(@RequestParam Integer subjectId) {
        List<QuizAttempt> attempts = quizAttemptRepository.findBySubjectIdOrderByScoreDesc(subjectId);

        List<QuizAttemptSummaryDTO> summaryList = attempts.stream()
                .map(attempt -> {
                    QuizAttemptSummaryDTO dto = new QuizAttemptSummaryDTO();
                    dto.setId(attempt.getId());

                    // Optional defensive null check
                    String subjectName = attempt.getSubject() != null ? attempt.getSubject().getName() : "Unknown";
                    dto.setSubjectName(subjectName);

                    dto.setScore(attempt.getScore());

                    if (attempt.getStartedAt() != null && attempt.getCompletedAt() != null) {
                        long duration = java.time.Duration.between(attempt.getStartedAt(), attempt.getCompletedAt()).getSeconds();
                        dto.setTimeTakenInSeconds(duration);
                    } else {
                        dto.setTimeTakenInSeconds(0L); // Default to 0 if not available

                    }

                    return dto;
                })
                .limit(15) // Only return top 15 scores
                .toList();

        return ResponseEntity.ok(summaryList);
    }

    // POST a new quiz attempt
    @PostMapping
    public ResponseEntity<?> createQuizAttempt(@RequestBody QuizAttemptDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findById(dto.getUserId());
            Optional<Subject> subjectOpt = subjectRepository.findById(dto.getSubjectId());

            if (userOpt.isEmpty() || subjectOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User or Subject not found"));
            }

            User user = userOpt.get();          // Managed entity
            Subject subject = subjectOpt.get(); // Managed entity

            QuizAttempt attempt = new QuizAttempt();
            attempt.setUser(user);                             // Link managed user
            attempt.setSubject(subject);                       // Link managed subject
            attempt.setScore(dto.getScore());
            attempt.setStartedAt(dto.getStartedAt() != null ? dto.getStartedAt() : LocalDateTime.now());
            attempt.setCompletedAt(dto.getCompletedAt() != null ? dto.getCompletedAt() : LocalDateTime.now());

            if (dto.getCompletedAt() != null && dto.getStartedAt() != null) {
                Duration duration = Duration.between(dto.getStartedAt(), dto.getCompletedAt());
                attempt.setDuration((int) duration.getSeconds());
            } else {
                attempt.setDuration(Math.toIntExact(dto.getDuration()));

            }

            QuizAttempt saved = quizAttemptRepository.save(attempt);

//  Populate summary from saved attempt
            QuizAttemptSummaryDTO summary = new QuizAttemptSummaryDTO();
            summary.setId(saved.getId());
            summary.setSubjectName(saved.getSubject().getName());
            summary.setScore(saved.getScore());
            summary.setTimeTakenInSeconds((saved.getTimeTakenInSeconds()));

            return ResponseEntity.status(201).body(summary);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    // Mark attempt as complete
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> markAttemptAsCompleted(@PathVariable int id) {
        Optional<QuizAttempt> attemptOpt = quizAttemptRepository.findById(id);

        if (attemptOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "QuizAttempt with ID " + id + " not found"));
        }

        QuizAttempt attempt = attemptOpt.get();
        attempt.setCompletedAt(LocalDateTime.now());
        quizAttemptRepository.save(attempt);

        return ResponseEntity.ok(Map.of("message", "Quiz marked as completed", "completedAt", attempt.getCompletedAt()));
    }

    // DELETE attempt
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizAttempt(@PathVariable int id) {
        if (!quizAttemptRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "QuizAttempt not found"));
        }

        quizAttemptRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




