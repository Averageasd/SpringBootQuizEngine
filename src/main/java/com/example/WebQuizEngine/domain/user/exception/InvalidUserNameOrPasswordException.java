package com.example.WebQuizEngine.domain.user.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserNameOrPasswordException extends AuthenticationException {
    public InvalidUserNameOrPasswordException(String message){
        super(message);
    }
}
