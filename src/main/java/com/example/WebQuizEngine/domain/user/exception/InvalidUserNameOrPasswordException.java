package com.example.WebQuizEngine.domain.user.exception;

public class InvalidUserNameOrPasswordException extends RuntimeException{
    public InvalidUserNameOrPasswordException(String message){
        super(message);
    }
}
