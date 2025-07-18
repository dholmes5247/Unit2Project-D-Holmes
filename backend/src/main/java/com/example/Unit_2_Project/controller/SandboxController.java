package com.example.Unit_2_Project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

