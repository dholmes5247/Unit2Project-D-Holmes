package com.example.Unit_2_Project.repository;

import com.example.Unit_2_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Additional added here if needed
}
