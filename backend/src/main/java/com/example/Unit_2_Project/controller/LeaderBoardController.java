package com.example.Unit_2_Project.controller;

import com.example.Unit_2_Project.dto.LeaderBoardEntryDTO;
import com.example.Unit_2_Project.dto.QuizAttemptDTO;
import com.example.Unit_2_Project.dto.SubjectDTO;
import com.example.Unit_2_Project.dto.UserDTO;
import com.example.Unit_2_Project.model.QuizAttempt;
import com.example.Unit_2_Project.repository.QuizAttemptRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Unit_2_Project.model.Subject;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.Page.page;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "*") // Allow frontend (e.g. http://localhost:5173) to access API
@AllArgsConstructor
public class LeaderBoardController {

    @Autowired
    private QuizAttemptRepository quizAttemptRepo;

    @GetMapping("/top")
    public ResponseEntity<List<QuizAttemptDTO>> getTopScores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending().and(Sort.by("duration").ascending()));
        Page<QuizAttempt> resultPage = quizAttemptRepo.findAllByOrderByScoreDescDurationAsc(pageable);
        List<QuizAttemptDTO> dtoList = resultPage.getContent().stream()
                .map(this::mapToDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }




    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<QuizAttemptDTO>> getBySubject(
            @PathVariable Integer subjectId) {
        List<QuizAttempt> attempts =
                quizAttemptRepo.findBySubjectIdOrderByScoreDescDurationAsc(subjectId);
        List<QuizAttemptDTO> dtoList = attempts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizAttemptDTO>> getByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("score").descending()
                .and(Sort.by("duration").ascending()));

        Page<QuizAttempt> resultPage = quizAttemptRepo
                .findByUserIdOrderByScoreDescDurationAsc(userId, pageable);

        List<QuizAttemptDTO> dtoList = resultPage.getContent().stream()
                .map(this::mapToDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    // ─── Helper: Convert Entity → DTO ───────────────────────────
    private QuizAttemptDTO mapToDto(QuizAttempt attempt) {
        QuizAttemptDTO dto = new QuizAttemptDTO();
        dto.setId(attempt.getId());
        dto.setScore(attempt.getScore());
        dto.setDuration(attempt.getDuration());

        // Map nested User → UserDTO
        UserDTO u = new UserDTO();
        u.setId(attempt.getUser().getId());
        u.setUsername(attempt.getUser().getUsername());
        u.setSchool(attempt.getUser().getSchool());
        dto.setUser(u);

        // Map nested Subject → SubjectDTO
        SubjectDTO s = new SubjectDTO();
        s.setId(attempt.getSubject().getId());
        s.setName(attempt.getSubject().getName());
        dto.setSubject(s);

        return dto;
    }
}

