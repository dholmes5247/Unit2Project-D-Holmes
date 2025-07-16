package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuizAttemptQuestionDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.model.QuizAttemptQuestion;
import com.example.Unit_2_Project.model.Question;
import com.example.Unit_2_Project.repository.QuizAttemptQuestionRepository;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/quiz-attempt-questions")
@CrossOrigin(origins = "*")
public class QuizAttemptQuestionController {

    @Autowired
    private QuizAttemptQuestionRepository quizAttemptQuestionRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private QuestionRepository questionRepository;

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
    public ResponseEntity<?> createQuizAttemptQuestion(@RequestBody QuizAttemptQuestionDTO dto) {
        Optional<Question> questionOpt = questionRepository.findById(dto.getQuestionId());
        Optional<QuizAttempt> quizAttemptOpt = quizAttemptRepository.findById(dto.getQuizAttemptId());

        if (questionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Question not found"));
        }

        if (quizAttemptOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "QuizAttempt not found"));
        }

        QuizAttemptQuestion quizAttemptQuestion = new QuizAttemptQuestion();
        quizAttemptQuestion.setAnswer(dto.isAnswer());
        quizAttemptQuestion.setCorrect(dto.isCorrect());
        quizAttemptQuestion.setQuestion(questionOpt.get());
        quizAttemptQuestion.setQuizAttempt(quizAttemptOpt.get());

        quizAttemptQuestionRepository.save(quizAttemptQuestion);
        return ResponseEntity.status(201).body(quizAttemptQuestion);
    }

    // PUT to update an existing quiz attempt question
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuizAttemptQuestion(@PathVariable int id, @RequestBody QuizAttemptQuestionDTO dto) {
        Optional<QuizAttemptQuestion> existingOpt = quizAttemptQuestionRepository.findById(id);
        Optional<Question> questionOpt = questionRepository.findById(dto.getQuestionId());
        Optional<QuizAttempt> quizAttemptOpt = quizAttemptRepository.findById(dto.getQuizAttemptId());

        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "QuizAttemptQuestion not found"));
        }

        if (questionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Question not found"));
        }

        if (quizAttemptOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "QuizAttempt not found"));
        }

        QuizAttemptQuestion existing = existingOpt.get();
        existing.setAnswer(dto.isAnswer());
        existing.setQuestion(questionOpt.get());
        existing.setQuizAttempt(quizAttemptOpt.get());

        quizAttemptQuestionRepository.save(existing);
        return ResponseEntity.ok(existing);
    }

    // DELETE a quiz attempt question
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuizAttemptQuestion(@PathVariable int id) {
        if (!quizAttemptQuestionRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of("error", "QuizAttemptQuestion not found"));
        }
        quizAttemptQuestionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



