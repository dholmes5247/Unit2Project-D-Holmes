package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.ExplanationDTO;
import com.example.Unit_2_Project.model.Question;
import com.example.Unit_2_Project.repository.QuestionRepository;
import com.example.Unit_2_Project.service.GoogleAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/explanations")
public class ExplanationController {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private GoogleAIService aiService;

    @GetMapping("/{questionId}")
    public ResponseEntity<ExplanationDTO> getExplanation(@PathVariable int questionId) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question not found"));

        String explanation = aiService.explainQuestion(
                question.getText(),
                question.getTopic()
        );

        ExplanationDTO dto = new ExplanationDTO(question.getText(), explanation);
        return ResponseEntity.ok(dto);
    }
}

