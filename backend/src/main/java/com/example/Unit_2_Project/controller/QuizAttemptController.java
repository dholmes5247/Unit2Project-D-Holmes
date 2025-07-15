package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz-attempts")
@CrossOrigin(origins = "*") // Enable CORS for frontend integration
public class QuizAttemptController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    // GET /api/quiz-attempts - Get all attempts
    @GetMapping
    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptRepository.findAll();
    }

    // GET /api/quiz-attempts/{id} - Get a specific attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttemptById(@PathVariable int id) {
        Optional<QuizAttempt> attempt = quizAttemptRepository.findById(id);

        if (attempt.isPresent()) {
            return ResponseEntity.ok(attempt.get());
        } else {
            return ResponseEntity
                    .status(404)
                    .body("QuizAttempt with ID " + id + " was not found.");
        }
    }


    // POST /api/quiz-attempts - Create a new quiz attempt
    @PostMapping
    public QuizAttempt createAttempt(@RequestBody QuizAttempt attempt) {
        return quizAttemptRepository.save(attempt);
    }

    // PUT /api/quiz-attempts/{id} - Update an existing quiz attempt
    @PutMapping("/{id}")
    public ResponseEntity<QuizAttempt> updateAttempt(@PathVariable int id, @RequestBody QuizAttempt updatedAttempt) {
        Optional<QuizAttempt> optional = quizAttemptRepository.findById(id);
        if (optional.isPresent()) {
            QuizAttempt existing = optional.get();
            existing.setScore(updatedAttempt.getScore());
            existing.setUser(updatedAttempt.getUser());
            existing.setSubject(updatedAttempt.getSubject());
            existing.setQuizAttemptQuestions(updatedAttempt.getQuizAttemptQuestions());
            return ResponseEntity.ok(quizAttemptRepository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/quiz-attempts/{id} - Delete a quiz attempt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttempt(@PathVariable int id) {
        if (quizAttemptRepository.existsById(id)) {
            quizAttemptRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

