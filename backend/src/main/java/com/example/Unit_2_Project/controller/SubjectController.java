package com.example.Unit_2_Project.controller;

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
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
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
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    // PUT to update an existing subject
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubject(@PathVariable int id, @RequestBody Subject updatedSubject) {
        Optional<Subject> optional = subjectRepository.findById(id);
        if (optional.isPresent()) {
            Subject existing = optional.get();
            existing.setName(updatedSubject.getName());
            existing.setDescription(updatedSubject.getDescription());
            existing.setImageUrl(updatedSubject.getImageUrl());
            subjectRepository.save(existing);
            return ResponseEntity.ok(existing);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Subject with ID " + id + " cannot be updated because it was not found.");
            return ResponseEntity.status(404).body(error);
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
