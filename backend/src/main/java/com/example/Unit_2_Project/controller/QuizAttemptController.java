package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuizAttemptDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import com.example.Unit_2_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz-attempts")
@CrossOrigin(origins = "*")
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

    // GET a quiz attempt by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuizAttemptById(@PathVariable int id) {
        return quizAttemptRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404)
                        .body((QuizAttempt) Map.of("error", "QuizAttempt with ID " + id + " not found")));
    }

    // POST a new quiz attempt using DTO
    @PostMapping
    public ResponseEntity<?> createQuizAttempt(@RequestBody QuizAttemptDTO dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        Optional<Subject> subjectOpt = subjectRepository.findById(dto.getSubjectId());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        if (subjectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Subject not found"));
        }

        QuizAttempt attempt = new QuizAttempt();
        attempt.setScore(dto.getScore());
        attempt.setUser(userOpt.get());
        attempt.setSubject(subjectOpt.get());

        return ResponseEntity.ok(quizAttemptRepository.save(attempt));
    }

    // DELETE a quiz attempt
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizAttempt(@PathVariable int id) {
        if (!quizAttemptRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "QuizAttempt not found"));
        }
        quizAttemptRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}


