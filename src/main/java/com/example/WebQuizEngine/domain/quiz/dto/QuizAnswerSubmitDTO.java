package com.example.WebQuizEngine.domain.quiz.dto;

import jakarta.validation.constraints.NotNull;

public record QuizAnswerSubmitDTO(
        @NotNull(message = "answers cannot be null")
        Integer[] answers
) {
}
