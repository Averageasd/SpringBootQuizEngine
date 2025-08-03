package com.example.WebQuizEngine.domain.user.exception;

public class CredentialsAlreadyExistException extends RuntimeException {
    public CredentialsAlreadyExistException(String message) {
        super(message);
    }
}
