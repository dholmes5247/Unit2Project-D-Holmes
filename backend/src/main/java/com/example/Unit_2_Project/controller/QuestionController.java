package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuestionDTO;
import com.example.Unit_2_Project.model.Question;
import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.repository.QuestionRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

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
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO dto) {
        Optional<Subject> subjectOpt = subjectRepository.findById(dto.getSubjectId());
        if (subjectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Subject not found"));
        }

        Question question = new Question(dto.getText(), dto.isAnswer(), dto.getDifficulty(), subjectOpt.get());
        question.setSubject(subjectOpt.get());

        // Validate that the question text is not empty
        if (question.getText() == null || question.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Question text cannot be empty"));
        }

        // Save the question and return the saved entity
        return ResponseEntity.ok(questionRepository.save(question));
    }

    // PUT update an existing question
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable int id, @RequestBody QuestionDTO dto) {
        Optional<Question> existingOpt = questionRepository.findById(id);
        Optional<Subject> subjectOpt = subjectRepository.findById(dto.getSubjectId());

        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Question not found"));
        }

        if (subjectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Subject not found"));
        }

        Question existing = existingOpt.get();
        existing.setText(dto.getText());
        existing.setAnswer(dto.isAnswer());
        existing.setDifficulty(dto.getDifficulty());
        existing.setSubject(subjectOpt.get());

        return ResponseEntity.ok(questionRepository.save(existing));
    }

    // DELETE a question
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id) {
        if (!questionRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "Question not found"));
        }
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


