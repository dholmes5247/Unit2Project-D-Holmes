package com.example.Unit_2_Project.repository;


import com.example.Unit_2_Project.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    // Additional queries can be added here if needed
    // For example, to find subjects by name or other criteria
}
