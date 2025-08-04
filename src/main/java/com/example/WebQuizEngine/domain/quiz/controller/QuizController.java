package com.example.WebQuizEngine.domain.quiz.controller;

import com.example.WebQuizEngine.domain.quiz.dto.CreateQuizRequestDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizAnswerResultDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizAnswerSubmitDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizResponseDTO;
import com.example.WebQuizEngine.domain.quiz.service.QuizService;
import com.example.WebQuizEngine.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @PostMapping(path = "/{userId}/quiz")
    public ResponseEntity<Void> createQuiz(
            @PathVariable UUID userId,
            @Valid @RequestBody CreateQuizRequestDTO createQuizRequestDTO) {
        userService.userWithIDExists(userId);
        quizService.createNewQuiz(userId, createQuizRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping(path = "/{userId}/quiz/{quizId}")
    public ResponseEntity<QuizResponseDTO> getSingleQuiz(
            @PathVariable UUID userId,
            @PathVariable UUID quizId) {
        userService.userWithIDExists(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.getQuiz(userId, quizId));
    }

    @PostMapping(path = "/{userId}/quiz/{quizId}/solve")
    public ResponseEntity<QuizAnswerResultDTO> answerQuiz(
            @PathVariable UUID userId,
            @PathVariable UUID quizId,
            @Valid @RequestBody QuizAnswerSubmitDTO quizAnswerSubmitDTO
            ){
        userService.userWithIDExists(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(quizService.answerQuiz(userId, quizId, quizAnswerSubmitDTO));
    }
}

