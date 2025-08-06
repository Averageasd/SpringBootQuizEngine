package com.example.WebQuizEngine.domain.quiz.exception;

import org.springframework.security.access.AccessDeniedException;

public class DeleteQuizForbiddenException extends AccessDeniedException {
    public DeleteQuizForbiddenException(String msg) {
        super(msg);
    }
}
