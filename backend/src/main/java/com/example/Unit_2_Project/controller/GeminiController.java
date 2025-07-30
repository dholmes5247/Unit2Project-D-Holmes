package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.GeminiRequestDTO;
import com.example.Unit_2_Project.service.GeminiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RestControllerAdvice
@RequestMapping("/api")

public class GeminiController {

    private final GeminiService geminiService;
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/explain")
    public ResponseEntity<String> explainQuestion(@Valid @RequestBody GeminiRequestDTO requestDTO) {
        String explanation = geminiService.explainQuestion(requestDTO.getQuestion(), requestDTO.getContext());
        return ResponseEntity.ok(explanation);
    }



}
