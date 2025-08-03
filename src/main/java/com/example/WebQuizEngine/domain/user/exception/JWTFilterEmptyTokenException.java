package com.example.WebQuizEngine.domain.user.exception;

public class JWTFilterEmptyTokenException extends RuntimeException {

    public JWTFilterEmptyTokenException(String message){
        super(message);
    }
}
