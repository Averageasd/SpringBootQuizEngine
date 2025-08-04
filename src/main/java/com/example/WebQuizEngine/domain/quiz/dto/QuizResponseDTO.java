package com.example.WebQuizEngine.domain.quiz.dto;

import java.util.UUID;

public record QuizResponseDTO(
        UUID id,
        String title,
        String text,
        String[] choices,
        Integer[] answers
) { }
