package com.example.WebQuizEngine.user.exception;

public class JWTFilterEmptyTokenException extends RuntimeException {

    public JWTFilterEmptyTokenException(String message){
        super(message);
    }
}
