package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow frontend access
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET /api/users - Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET /api/users/{id} - Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.status(404)
                                .body("User with ID " + id + " was not found."));
    }

    // POST /api/users - Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // PUT /api/users/{id} - Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User existing = optional.get();
            existing.setUsername(updatedUser.getUsername());
            existing.setQuizAttempts(updatedUser.getQuizAttempts());
            return ResponseEntity.ok(userRepository.save(existing));
        } else {
            return ResponseEntity.notFound().build();
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

