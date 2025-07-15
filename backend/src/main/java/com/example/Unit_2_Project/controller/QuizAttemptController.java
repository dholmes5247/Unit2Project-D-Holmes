package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz-attempts")
@CrossOrigin(origins = "*") // Enable CORS for frontend integration
public class QuizAttemptController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    // GET all quiz attempts
    @GetMapping
    public List<QuizAttempt> getAllAttempts() {
        return quizAttemptRepository.findAll();
    }

    // GET a specific quiz attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAttemptById(@PathVariable int id) {
        Optional<QuizAttempt> attempt = quizAttemptRepository.findById(id);
        if (attempt.isPresent()) {
            return ResponseEntity.ok(attempt.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttempt with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // POST a new quiz attempt
    @PostMapping
    public QuizAttempt createAttempt(@RequestBody QuizAttempt attempt) {
        return quizAttemptRepository.save(attempt);
    }

    // PUT to update an existing quiz attempt
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAttempt(@PathVariable int id, @RequestBody QuizAttempt updatedAttempt) {
        Optional<QuizAttempt> optional = quizAttemptRepository.findById(id);
        if (optional.isPresent()) {
            QuizAttempt existing = optional.get();
            existing.setScore(updatedAttempt.getScore());
            existing.setUser(updatedAttempt.getUser());
            existing.setSubject(updatedAttempt.getSubject());
            existing.setQuizAttemptQuestions(updatedAttempt.getQuizAttemptQuestions());
            quizAttemptRepository.save(existing);
            return ResponseEntity.ok(existing);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttempt with ID " + id + " cannot be updated because it was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // DELETE a quiz attempt
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAttempt(@PathVariable int id) {
        if (quizAttemptRepository.existsById(id)) {
            quizAttemptRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "QuizAttempt with ID " + id + " was successfully deleted.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttempt with ID " + id + " could not be deleted because it does not exist.");
            return ResponseEntity.status(404).body(error);
        }
    }
}

