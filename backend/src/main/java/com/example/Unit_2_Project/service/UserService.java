package com.example.Unit_2_Project.service;

import com.example.Unit_2_Project.dto.UserSignupDTO;
import com.example.Unit_2_Project.model.User;
import com.example.Unit_2_Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registers a new user and returns the created User object
    public User registerUser(UserSignupDTO userDto) {
        System.out.println("Attempting to register user: " + userDto.getEmail());

        // Check if email is already used
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email is already in use.");
        }

        // Check if username is already taken
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username is already taken.");
        }

        // Create and populate new User entity
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setSchool(userDto.getSchool());

        // Securely hash the password before saving
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Persist the user to the database
        return userRepository.save(user);
    }
    }


