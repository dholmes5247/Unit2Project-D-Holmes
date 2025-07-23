package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.*;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import com.example.Unit_2_Project.repository.SubjectRepository;
import com.example.Unit_2_Project.repository.UserRepository;
import com.example.Unit_2_Project.security.JwtUtil;
import com.example.Unit_2_Project.service.UserService;
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
    private UserService userService; // Injected service for user operations

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
    @GetMapping("/id/{id}")
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
    @GetMapping("/id/{id}/profile")
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

    // POST /api/users/signup – Creates new user and returns success message
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupDTO userDto) {
        try {
            User newUser = userService.registerUser(userDto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", Map.of(
                    "name", newUser.getName(),
                    "email", newUser.getEmail(),
                    "school", newUser.getSchool()
            ));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("message", e.getMessage())
            );
        }
    }


// POST /api/users/login – Authenticates user and returns a JWT + user info
@PostMapping("/login")
public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserLoginDTO loginDTO) {

    // 🔍 Step 1: Find the user by email
    Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());

    if (optionalUser.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "User not found"));
    }

    User user = optionalUser.get();

    // 🔐 Step 2: Verify password
    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid password"));
    }

    // 🧠 Step 3: Generate JWT
    String token = jwtUtil.generateToken(user.getEmail());

    // ✨ Step 4: Build a full UserProfileDTO to send to frontend
    UserProfileDTO profileDTO = new UserProfileDTO();
    profileDTO.setId(user.getId()); // ✅ This line ensures user.id is available in the frontend
    profileDTO.setUsername(user.getName());
    profileDTO.setEmail(user.getEmail());
    profileDTO.setSchool(user.getSchool()); // if applicable
    profileDTO.setQuizAttempts(List.of());  // optionally populate later

    // 🚀 Step 5: Return token + full user profile as response
    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("user", profileDTO); // 👈 This replaces the manual Map and ensures full user data

    return ResponseEntity.ok(response);
}






    // PUT /api/users/{id} - Update an existing user
    @PutMapping("/id/{id}")
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
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



