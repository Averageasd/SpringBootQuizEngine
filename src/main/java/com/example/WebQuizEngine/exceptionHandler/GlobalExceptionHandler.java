package com.example.WebQuizEngine.exceptionHandler;

import com.example.WebQuizEngine.user.exception.CredentialsAlreadyExistException;
import com.example.WebQuizEngine.user.exception.InvalidUserNameOrPasswordException;
import com.example.WebQuizEngine.user.exception.JWTFilterEmptyTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidUserNameOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUserNameOrPasswordException(InvalidUserNameOrPasswordException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({CredentialsAlreadyExistException.class})
    public ResponseEntity<Object> handleCredentialsAlreadyExistException(CredentialsAlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler({JWTFilterEmptyTokenException.class})
    public ResponseEntity<Object> handleJWTFilterEmptyTokenException(JWTFilterEmptyTokenException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalErrorException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }


}
