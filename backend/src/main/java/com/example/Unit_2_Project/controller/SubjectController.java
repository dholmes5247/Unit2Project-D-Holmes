package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.Subject;
import com.example.Unit_2_Project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*") // Allow frontend access
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // GET /api/subjects - Get all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // GET /api/subjects/{id} - Get a subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable int id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        return subject.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.status(404)
                                .body("Subject with ID " + id + " was not found."));
    }

    // POST /api/subjects - Create a new subject
    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectRepository.save(subject);
    }

    // PUT /api/subjects/{id} - Update an existing subject
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable int id, @RequestBody Subject updatedSubject) {
        Optional<Subject> optional = subjectRepository.findById(id);
        if (optional.isPresent()) {
            Subject existing = optional.get();
            existing.setName(updatedSubject.getName());
            existing.setQuestions(updatedSubject.getQuestions());
            existing.setQuizAttempts(updatedSubject.getQuizAttempts());
            return ResponseEntity.ok(subjectRepository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/subjects/{id} - Delete a subject
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable int id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
