package com.example.WebQuizEngine.domain.quiz.dto;

import com.example.WebQuizEngine.domain.quiz.validation.ChoicesAnswersLengthCheckCustomConstraint;
import com.example.WebQuizEngine.domain.quiz.validation.ChoicesCustomConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

@ChoicesAnswersLengthCheckCustomConstraint(
        choices = "choices",
        answers = "answers"
)
public record CreateQuizRequestDTO(
        @NotNull(message = "title cannot be null")
        @NotBlank(message = "title cannot be blank")
        @Length(min = 1, max = 30, message = "Title length must be between 1 and 30 characters")
        String title,

        @NotNull(message = "text cannot be null")
        @NotBlank(message = "text cannot be blank")
        @Length(min = 1, max = 250, message = "Text length must be between 1 and 250 characters")
        String text,

        @NotNull(message = "choices cannot be null")
        @Size(min = 2, max = 4, message = "There must be at least 2 choices")
        @ChoicesCustomConstraint
        String[] choices,

        @NotNull(message = "answers cannot be null")
        Integer[] answers
) {
}
