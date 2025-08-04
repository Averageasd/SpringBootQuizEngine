package com.example.WebQuizEngine.domain.quiz.dto;

import com.example.WebQuizEngine.domain.quiz.validation.ChoicesCustomConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record CreateQuizRequestDTO(
        @NotBlank(message = "title cannot be blank")
        @Column(name = "title", length = 50, nullable = false)
        @Length(min = 1, max = 30, message = "Title length must be between 1 and 30 characters")
        String title,

        @NotBlank(message = "text cannot be blank")
        @Column(name = "text", length = 250, nullable = false)
        @Length(min = 1, max = 250, message = "Text length must be between 1 and 250 characters")
        String text,

        @NotNull(message = "choices cannot be empty")
        @Column(name = "choices", length = 4, nullable = false)
        @Size(min = 2, max = 4, message = "There must be at least 2 choices")
        @ChoicesCustomConstraint
        String[] choices,

        @Column(name = "answers")
        Integer[] answers
) {
}
