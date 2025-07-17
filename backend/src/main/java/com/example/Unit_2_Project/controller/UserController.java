package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.*;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import com.example.Unit_2_Project.repository.UserRepository;
import com.example.Unit_2_Project.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow frontend access


public class UserController {
    // Repositories and services

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injected

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
        List<QuizAttemptSummaryDTO> summaries = quizAttemptRepository.findByUserId(user.getId())

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

        profile.setQuizAttempts(summaries);

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

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDTO userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email is already in use, try again.");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username is already taken, try again.");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName()); // <-- Required
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setSchool(userDto.getSchool());

        // Hash the password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


    // POST /api/users/login - Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO loginDTO) {
        // 🔍 Find user by email
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check if the password matches (using the hashed password)
            if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {

                // Generate JWT using user email or ID
                String token = jwtUtil.generateToken(user.getEmail());

                // Return token in response
                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                return ResponseEntity.ok(response); // 200 OK with JWT
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
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



