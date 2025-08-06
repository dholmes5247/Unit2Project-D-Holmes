package com.example.Unit_2_Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Unit2ProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(Unit2ProjectApplication.class, args);
	}
	// bean definition placeholder if needed in future and to get

}
