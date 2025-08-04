package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuizAttemptDTO;

import com.example.Unit_2_Project.dto.SubjectDTO;
import com.example.Unit_2_Project.dto.UserDTO;
import com.example.Unit_2_Project.model.QuizAttempt;

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

/*    // GET single attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizAttempt> getQuizAttemptById(@PathVariable int id) {
        QuizAttempt attempt = quizAttemptRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "QuizAttempt with ID " + id + " not found"
                ));
        return ResponseEntity.ok(attempt);
    }
*/


    // POST a new quiz attempt
    @PostMapping
    public ResponseEntity<?> submitAttempt(@RequestBody QuizAttemptDTO attemptDTO) {
        try {
            // Extract userId safely
            Integer userId = attemptDTO.getUser() != null ? attemptDTO.getUser().getId() : null;
            if (userId == null) {
                return ResponseEntity.badRequest().body("Missing user ID.");
            }

            // Extract subjectId safely
            Integer subjectId = attemptDTO.getSubject() != null ? attemptDTO.getSubject().getId() : null;
            if (subjectId == null) {
                return ResponseEntity.badRequest().body("Missing subject ID.");
            }

            //  Calculate duration or use provided timeTakenInSeconds
            Long duration = (attemptDTO.getStartedAt() != null && attemptDTO.getCompletedAt() != null)
                    ? Duration.between(attemptDTO.getStartedAt(), attemptDTO.getCompletedAt()).getSeconds()
                    : attemptDTO.getTimeTakenInSeconds();

            //  Construct new QuizAttempt entity
            QuizAttempt attempt = new QuizAttempt();
            attempt.setScore(attemptDTO.getScore());
            attempt.setStartedAt(attemptDTO.getStartedAt());
            attempt.setCompletedAt(attemptDTO.getCompletedAt());
            attempt.setDuration(duration); //  clean and explicit
            attempt.setTotalQuestions(attemptDTO.getTotalQuestions());


            // 🔧 You can fetch full User and Subject entities here if needed
            attempt.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            attempt.setSubject(subjectRepository.findById(subjectId).orElseThrow(() -> new RuntimeException("Subject not found")));

            QuizAttempt saved = quizAttemptRepository.save(attempt);

            return ResponseEntity.ok(saved); // or map back to DTO if needed
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving attempt: " + ex.getMessage());
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




