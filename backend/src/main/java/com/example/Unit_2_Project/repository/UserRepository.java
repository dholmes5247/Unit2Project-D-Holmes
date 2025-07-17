package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Additional
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    // Custom query method to find a user by email
    Optional<User> findByEmail(String email);
}
