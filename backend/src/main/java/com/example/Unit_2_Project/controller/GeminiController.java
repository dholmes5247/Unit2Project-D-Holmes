package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.GeminiRequestDTO;
import com.example.Unit_2_Project.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class GeminiController {

    private final GeminiService geminiService;
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/explain")
    public ResponseEntity<String> explainQuestion(@RequestBody GeminiRequestDTO requestDTO) {
        String explanation = geminiService.explainQuestion(requestDTO.getQuestion(), requestDTO.getContext());
        return ResponseEntity.ok(explanation);
    }


}
