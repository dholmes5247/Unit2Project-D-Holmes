package com.example.Unit_2_Project.controller;

import java.util.Optional;
import java.util.List;

import com.example.Unit_2_Project.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Unit_2_Project.repository.QuestionRepository;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*") // Allow frontend access

public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;
    // GET /api/questions - Get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    // GET /api/questions/{id} - Get a question by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable int id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body("Question with ID " + id + " was not found."));
    }



}
