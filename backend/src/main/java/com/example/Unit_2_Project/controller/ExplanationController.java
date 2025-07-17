package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.ExplanationDTO;
import com.example.Unit_2_Project.model.Question;
import com.example.Unit_2_Project.repository.QuestionRepository;
import com.example.Unit_2_Project.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/explanations")
public class ExplanationController {

    private final QuestionRepository questionRepo;
    private final GeminiService geminiService;

    public ExplanationController(QuestionRepository questionRepo,
                                 GeminiService geminiService) {
        this.questionRepo = questionRepo;
        this.geminiService = geminiService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ExplanationDTO> getExplanation(@PathVariable int questionId) {
        Question q = questionRepo.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question not found"));

        String context = q.getSubject() != null
                ? q.getSubject().getName()
                : null;
        String expl = geminiService.explainQuestion(q.getText(), context);

        return ResponseEntity.ok(
                new ExplanationDTO(
                        q.getId(),       // Integer id
                        q.getText(),     // String question
                        expl             // String explanation
                )
        );


    }
}


