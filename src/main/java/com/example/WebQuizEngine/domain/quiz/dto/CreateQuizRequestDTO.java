package com.example.WebQuizEngine.domain.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateQuizRequestDTO(
        @NotBlank(message = "userName should not be blank")
        @Length(min = 3, max = 30, message = "Name length must be between 3 and 30 characters")
        String userName,

        @NotBlank(message = "userPassword should not be blank")
        @Length(min = 8, max = 50, message = "Password length must be between 8 and 50 characters")
        String userPassword
){}
