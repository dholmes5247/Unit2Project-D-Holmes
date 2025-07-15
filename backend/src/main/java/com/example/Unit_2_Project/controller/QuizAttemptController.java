package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping equestMapping("/api/quiz-attempts")
@CrossOrigin(origins = "*")  //  Enable CORS for frontend access

public class QuizAttemptController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

// GET /api/quiz-attempts - get all quiz attempts
    @GetMapping
    public List<QuizAttempt> getAllQuizAttempts() {
        return quizAttemptRepository.findAll();
    }
// GET /api/quiz-attempts/{id} - Get a specific attempt by ID
    @GetMapping
    public QuizAttempt getQuizAttemptById(@PathVariable int id) {
        return quizAttemptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz Attempt not found with id " + id));
    }
 // POST /api/quiz-attempts - Create a new quiz attempt
    @PostMapping
    public QuizAttempt createQuizAttempt(@RequestBody QuizAttempt quizAttempt) {
        return quizAttemptRepository.save(quizAttempt);
    }

}
