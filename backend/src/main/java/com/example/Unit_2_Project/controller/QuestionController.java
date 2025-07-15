package com.example.Unit_2_Project.controller;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Object> getQuestionById(@PathVariable int id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Question with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // POST /api/questions - Create a new question
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.status(201).body(savedQuestion); // Return the saved question with status 201
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        }
        return ResponseEntity.notFound().build(); // HTTP 404
    }
}

