package com.example.Unit_2_Project.controller;



import com.example.Unit_2_Project.model.QuizAttemptQuestion;
import com.example.Unit_2_Project.repository.QuizAttemptQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz-attempt-questions")
@CrossOrigin(origins = "*") // Allow frontend access
public class QuizAttemptQuestionController {

    @Autowired
    private QuizAttemptQuestionRepository quizAttemptQuestionRepository;

    // GET all quiz attempt questions
    @GetMapping
    public List<QuizAttemptQuestion> getAllQuizAttemptQuestions() {
        return quizAttemptQuestionRepository.findAll();
    }

    // GET a specific quiz attempt question by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getQuizAttemptQuestionById(@PathVariable int id) {
        Optional<QuizAttemptQuestion> attemptQuestion = quizAttemptQuestionRepository.findById(id);
        if (attemptQuestion.isPresent()) {
            return ResponseEntity.ok(attemptQuestion.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttemptQuestion with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // POST a new quiz attempt question
    @PostMapping
    public QuizAttemptQuestion createQuizAttemptQuestion(@RequestBody QuizAttemptQuestion quizAttemptQuestion) {
        return quizAttemptQuestionRepository.save(quizAttemptQuestion);
    }

    // PUT to update an existing quiz attempt question
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateQuizAttemptQuestion(@PathVariable int id, @RequestBody QuizAttemptQuestion updatedQuizAttemptQuestion) {
        Optional<QuizAttemptQuestion> optional = quizAttemptQuestionRepository.findById(id);
        if (optional.isPresent()) {
            QuizAttemptQuestion existing = optional.get();
            existing.setUserAnswer(updatedQuizAttemptQuestion.isUserAnswer());

            existing.setQuestion(updatedQuizAttemptQuestion.getQuestion());
            existing.setQuizAttempt(updatedQuizAttemptQuestion.getQuizAttempt());
            quizAttemptQuestionRepository.save(existing);
            return ResponseEntity.ok(existing);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttemptQuestion with ID " + id + " cannot be updated because it was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // DELETE a quiz attempt question
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuizAttemptQuestion(@PathVariable int id) {
        if (quizAttemptQuestionRepository.existsById(id)) {
            quizAttemptQuestionRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "QuizAttemptQuestion with ID " + id + " was successfully deleted.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "QuizAttemptQuestion with ID " + id + " could not be deleted because it does not exist.");
            return ResponseEntity.status(404).body(error);
        }
    }
}

