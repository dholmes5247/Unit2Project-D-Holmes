package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.SubjectDTO;
import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*") // Allow frontend access
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // GET all subjects
    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream().map(subject -> {
            SubjectDTO dto = new SubjectDTO();
            dto.setId(subject.getId());
            dto.setName(subject.getName());
            dto.setDescription(subject.getDescription());
            dto.setImageUrl(subject.getImageUrl());
            dto.setQuestionCount(subject.getQuestions() != null ? subject.getQuestions().size() : 0);
            return dto;
        }).collect(Collectors.toList());
    }


// GET a specific subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubjectById(@PathVariable int id) {
        Optional<Subject> optional = subjectRepository.findById(id);

        if (optional.isPresent()) {
            Subject subject = optional.get();

            SubjectDTO dto = new SubjectDTO();
            dto.setId(subject.getId());
            dto.setName(subject.getName());
            dto.setDescription(subject.getDescription());
            dto.setImageUrl(subject.getImageUrl());
            dto.setQuestionCount(subject.getQuestions() != null ? subject.getQuestions().size() : 0);

            return ResponseEntity.ok(dto);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Subject with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }


    // POST a new subject
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setDescription(dto.getDescription());
        subject.setImageUrl(dto.getImageUrl());

        Subject saved = subjectRepository.save(subject);

        SubjectDTO responseDto = new SubjectDTO();
        responseDto.setId(saved.getId());
        responseDto.setName(saved.getName());
        responseDto.setDescription(saved.getDescription());
        responseDto.setImageUrl(saved.getImageUrl());
        responseDto.setQuestionCount(saved.getQuestions() != null ? saved.getQuestions().size() : 0);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // PUT to update an existing subject
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable int id, @RequestBody SubjectDTO dto) {
        Optional<Subject> optional = subjectRepository.findById(id);

        if (optional.isPresent()) {
            Subject subject = optional.get();
            subject.setName(dto.getName());
            subject.setDescription(dto.getDescription());
            subject.setImageUrl(dto.getImageUrl());

            Subject updated = subjectRepository.save(subject);

            SubjectDTO responseDto = new SubjectDTO();
            responseDto.setId(updated.getId());
            responseDto.setName(updated.getName());
            responseDto.setDescription(updated.getDescription());
            responseDto.setImageUrl(updated.getImageUrl());
            responseDto.setQuestionCount(updated.getQuestions() != null ? updated.getQuestions().size() : 0);

            return ResponseEntity.ok(responseDto);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Subject with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }



    // DELETE a subject
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubject(@PathVariable int id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Subject with ID " + id + " was successfully deleted.");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Subject with ID " + id + " could not be deleted because it does not exist.");
            return ResponseEntity.status(404).body(error);
        }
    }
}
