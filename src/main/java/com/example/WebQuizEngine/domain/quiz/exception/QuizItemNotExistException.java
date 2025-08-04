package com.example.WebQuizEngine.domain.quiz.exception;

public class QuizItemNotExistException extends RuntimeException {
    public QuizItemNotExistException(String message){
        super(message);
    }
}
