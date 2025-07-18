package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.UserRepository;
import com.example.Unit_2_Project.dto.UserSignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(UserSignupDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already in use, try again.");
        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username is already taken, try again.");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setSchool(userDto.getSchool());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}

