package com.example.WebQuizEngine.domain.quiz.controller;

import com.example.WebQuizEngine.domain.quiz.dto.*;
import com.example.WebQuizEngine.domain.quiz.service.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/{userId}/quiz")
    public ResponseEntity<Void> createQuiz(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateQuizRequestDTO createQuizRequestDTO) {
        quizService.createNewQuiz(userId, createQuizRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(path = "/{userId}/quiz/{quizId}")
    public ResponseEntity<QuizResponseDTO> getSingleQuiz(
            @PathVariable UUID userId,
            @PathVariable UUID quizId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.getQuiz(userId, quizId));
    }

    @PostMapping(path = "/{userId}/quiz/{quizId}/solve")
    public ResponseEntity<QuizAnswerResultDTO> answerQuiz(
            @PathVariable UUID userId,
            @PathVariable UUID quizId,
            @Valid @RequestBody QuizAnswerSubmitDTO quizAnswerSubmitDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.answerQuiz(userId, quizId, quizAnswerSubmitDTO));
    }

    @GetMapping(path = "/{userId}/quiz")
    public ResponseEntity<Page<QuizResponseDTO>> getQuizzes(
            @PathVariable UUID userId,
            @RequestParam @NotNull @Min(0) int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.getQuizzes(userId, page));
    }

    @GetMapping(path = "/{userId}/quiz/completed")
    public ResponseEntity<Page<CompletedQuizHistoryResponseDTO>> getCompletedQuizzes(
            @PathVariable UUID userId,
            @RequestParam @NotNull @Min(0) int page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.getCompletedQuizzes(userId, page));
    }
}

