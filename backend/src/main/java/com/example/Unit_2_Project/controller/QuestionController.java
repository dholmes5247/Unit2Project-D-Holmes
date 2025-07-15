package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.Question;
import com.example.Unit_2_Project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // GET all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // GET a question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Question with ID " + id + " not found"));
    }


    // POST a new question
    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionRepository.save(question);
    }

    // PUT update an existing question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question updated) {
        return questionRepository.findById(id).map(existing -> {
            existing.setText(updated.getText());
            existing.setAnswer(updated.isAnswer());
            existing.setDifficulty(updated.getDifficulty());
            existing.setSubject(updated.getSubject());
            return ResponseEntity.ok(questionRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE a question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable int id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


