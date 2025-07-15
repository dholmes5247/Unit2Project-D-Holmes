package com.example.Unit_2_Project.controller;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.Unit_2_Project.repository.QuestionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*") // Allow frontend access


public class QuestionController {
}
