package com.example.WebQuizEngine.user.exception;

public class InvalidUserNameOrPasswordException extends RuntimeException{
    public InvalidUserNameOrPasswordException(String message){
        super(message);
    }
}
