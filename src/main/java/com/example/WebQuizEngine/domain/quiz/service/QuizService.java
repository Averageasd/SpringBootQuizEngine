package com.example.WebQuizEngine.domain.quiz.service;

import com.example.WebQuizEngine.domain.quiz.dto.CreateQuizRequestDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizAnswerResultDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizAnswerSubmitDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizResponseDTO;
import com.example.WebQuizEngine.domain.quiz.exception.QuizItemNotExistException;
import com.example.WebQuizEngine.domain.quiz.exception.errorMessage.ErrorMessages;
import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;
import com.example.WebQuizEngine.domain.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public QuizResponseDTO getQuiz(UUID userId, UUID quizId) {
        QuizEntity quizEntity = quizRepository
                .findSingleQuizItemWithQuizIdAndUserId(quizId, userId);
        if (quizEntity == null) {
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

    public QuizAnswerResultDTO answerQuiz(UUID userId, UUID quizId, QuizAnswerSubmitDTO quizAnswerSubmitDTO) {
        QuizEntity quizEntity = quizRepository
                .findSingleQuizItemWithQuizIdAndUserId(quizId, userId);

        Integer[] submittedAnswers = quizAnswerSubmitDTO.answers();
        Integer[] quizAnswers = quizEntity.getAnswers();
        Arrays.sort(submittedAnswers);
        Arrays.sort(quizAnswers);
        String feedback = QuizAnswerResultDTO.SUCCESS_MESSAGE;
        boolean success = true;
        QuizAnswerResultDTO quizAnswerResultDTO = new QuizAnswerResultDTO();

        // submitted answers have different length from quizEntity answers
        if (submittedAnswers.length != quizAnswers.length) {
            feedback = QuizAnswerResultDTO.FAIL_MESSAGE;
            success = false;

        } else {

            // both submitted answers and quiz answers are empty meaning that
            // all choices must be false. then this evaluates to true
            if (quizAnswers.length == 0) {
                feedback = QuizAnswerResultDTO.SUCCESS_MESSAGE;
            } else {

                // submitted answers and quiz answers do not match
                for (int i = 0; i < submittedAnswers.length; i++) {
                    int currentAnswer = submittedAnswers[i];
                    if (currentAnswer != quizAnswers[i]) {
                        feedback = QuizAnswerResultDTO.FAIL_MESSAGE;
                        success = false;
                        break;
                    }
                }
            }
        }

        quizAnswerResultDTO.setFeedback(feedback);
        quizAnswerResultDTO.setSuccess(success);
        return quizAnswerResultDTO;
    }
}
