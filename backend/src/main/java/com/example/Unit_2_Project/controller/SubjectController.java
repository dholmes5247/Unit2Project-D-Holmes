package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.SubjectDTO;
import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*") // Allow frontend access
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // GET all subjects
    @GetMapping  // kept getting type mismatch error
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDTO> dtos = new ArrayList<>();

        for (Subject subject : subjects) {
            SubjectDTO dto = new SubjectDTO();
            dto.setName(subject.getName());
            dto.setDescription(subject.getDescription());
            dto.setImageUrl(subject.getImageUrl());
            dtos.add(dto);
        }

        return dtos;
    }

    // GET a specific subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubjectById(@PathVariable int id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            return ResponseEntity.ok(subject.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Subject with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // POST a new subject
    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setDescription(subjectDTO.getDescription());
        subject.setImageUrl(subjectDTO.getImageUrl());

        Subject saved = subjectRepository.save(subject);
        return ResponseEntity.ok(saved); // optionally convert back to a SubjectResponseDTO
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

            subjectRepository.save(subject);
            return ResponseEntity.ok(subject);
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Subject with ID " + id + " not found."));
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
