package com.example.WebQuizEngine.domain.quiz.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompletedQuizHistoryResponseDTO (
    UUID quizId,
    LocalDateTime completedAt
){}
