package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.QuizAttemptSummaryDTO;
import com.example.Unit_2_Project.dto.UserDTO;
import com.example.Unit_2_Project.dto.UserProfileDTO;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import com.example.Unit_2_Project.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow frontend access
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // GET /api/users - Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET /api/users/{id} - Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // GET user profile including quiz attempt summary
    @GetMapping("/{id}/profile")
    public ResponseEntity<Object> getUserProfile(@PathVariable int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        User user = optionalUser.get();

        // Map User entity to UserProfileDTO
        UserProfileDTO profile = new UserProfileDTO();
        profile.setId(user.getId());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());

        // Map quiz attempts to summary DTOs
        List<QuizAttemptSummaryDTO> summaries = QuizAttemptRepository.findByUserId(user.getId())
                .stream()
                .map(attempt -> {
                    QuizAttemptSummaryDTO summary = new QuizAttemptSummaryDTO();
                    summary.setId(attempt.getId());
                    summary.setScore(attempt.getScore());
                    summary.setTimeTakenInSeconds(attempt.getTimeTakenInSeconds());

                    // Look up subject name
                    String subjectName = attempt.getSubject() != null
                            ? attempt.getSubject().getName()
                            : "Unknown Subject";
                    summary.setSubjectName(subjectName);

                    return summary;
                })
                .toList();

        profile.setQuizHistory(summaries);

        return ResponseEntity.ok(profile);
    }


    // POST /api/users - Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        // Create User entity from UserDTO
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setSchool(userDTO.getSchool());
        user.setPassword(userDTO.getPassword()); // Consider hashing the password before saving

        // Save the user to the database
        userRepository.save(user);
        return ResponseEntity.status(201).body(user); // Return the created user
    }

    // PUT /api/users/{id} - Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User existingUser = optional.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setName(userDTO.getName());
            existingUser.setSchool(userDTO.getSchool());
            existingUser.setPassword(userDTO.getPassword()); // Consider hashing the password before saving

            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User with ID " + id + " was not found.");
            return ResponseEntity.status(404).body(error);
        }
    }

    // DELETE /api/users/{id} - Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



