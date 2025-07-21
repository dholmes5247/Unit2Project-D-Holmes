package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Unit_2_Project.model.User;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class SandboxController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicMessage() {
        return ResponseEntity.ok("Sandbox access successful!");
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echoPayload(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(payload);
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-test-user")
    public ResponseEntity<String> saveTestUser() {
        User testUser = new User();
        testUser.setUsername("sandboxUser");
        testUser.setName("Sandbox Tester");
        testUser.setEmail("sandbox@example.com");
        testUser.setPassword("$2a$10$examplehashedpassword"); // skip hashing for sandbox
        testUser.setSchool("Sandbox School");

        userRepository.save(testUser);
        return ResponseEntity.ok("User saved from sandbox.");
    }

}

