package com.example.WebQuizEngine.exceptionHandler;

import com.example.WebQuizEngine.domain.quiz.exception.DeleteQuizForbiddenException;
import com.example.WebQuizEngine.domain.user.exception.CredentialsAlreadyExistException;
import com.example.WebQuizEngine.domain.user.exception.InvalidUserNameOrPasswordException;
import com.example.WebQuizEngine.domain.user.exception.JWTFilterEmptyTokenException;
import com.example.WebQuizEngine.domain.user.exception.UserNotExistException;
import com.example.WebQuizEngine.util.ErrorMessageGeneratorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMessageGeneratorUtil errorMessageGeneratorUtil;

    public GlobalExceptionHandler(ErrorMessageGeneratorUtil errorMessageGeneratorUtil) {
        this.errorMessageGeneratorUtil = errorMessageGeneratorUtil;
    }

    @ExceptionHandler({InvalidUserNameOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUserNameOrPasswordException(InvalidUserNameOrPasswordException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        errorMessageGeneratorUtil
                                .getBindingErrorMessages(e.getBindingResult()));
    }

    @ExceptionHandler({UserNotExistException.class})
    public ResponseEntity<Object> handleUserNotExistException(UserNotExistException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler({DeleteQuizForbiddenException.class})
    public ResponseEntity<Object> handleDeleteQuizForbiddenException(DeleteQuizForbiddenException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalErrorException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
