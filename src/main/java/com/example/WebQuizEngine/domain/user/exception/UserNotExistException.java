package com.example.WebQuizEngine.domain.user.exception;

public class UserNotExistException extends RuntimeException{

    public UserNotExistException(String message){
        super(message);
    }
}
