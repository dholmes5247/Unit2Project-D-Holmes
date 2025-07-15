package com.example.Unit_2_Project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity                              // JPA will map this class to a "subject" table
@Data                                // Lombok generates getters, setters, toString, equals, hashCode
@NoArgsConstructor                   // Required by JPA
@AllArgsConstructor                  // Useful for full construction

@Getter
@Setter

public class Subject {




}

