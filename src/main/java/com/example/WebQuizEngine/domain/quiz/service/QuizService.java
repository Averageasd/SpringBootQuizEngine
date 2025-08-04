package com.example.WebQuizEngine.domain.quiz.service;

import com.example.WebQuizEngine.domain.quiz.dto.CreateQuizRequestDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizResponseDTO;
import com.example.WebQuizEngine.domain.quiz.exception.QuizItemNotExistException;
import com.example.WebQuizEngine.domain.quiz.exception.errorMessage.ErrorMessages;
import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;
import com.example.WebQuizEngine.domain.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional
    public void createNewQuiz(UUID userId, @Valid CreateQuizRequestDTO createQuizRequestDTO) {
        QuizEntity quizEntity = new QuizEntity(
                createQuizRequestDTO.title(),
                createQuizRequestDTO.text(),
                createQuizRequestDTO.choices(),
                createQuizRequestDTO.answers(),
                userId
        );
        quizRepository.save(quizEntity);
    }

    public QuizResponseDTO getQuiz(UUID userId, UUID quizId){
        QuizEntity quizEntity = quizRepository.findSingleQuizItemWithQuizIdAndUserId(quizId, userId);
        if (quizEntity == null){
            throw new QuizItemNotExistException(ErrorMessages.QUIZ_NOT_EXIST_EXCEPTION);
        }
        return new QuizResponseDTO(
                quizEntity.getId(),
                quizEntity.getTitle(),
                quizEntity.getText(),
                quizEntity.getChoices(),
                quizEntity.getAnswers()
        );
    }
}
