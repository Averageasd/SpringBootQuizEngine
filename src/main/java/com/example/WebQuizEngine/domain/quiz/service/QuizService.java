package com.example.WebQuizEngine.domain.quiz.service;

import com.example.WebQuizEngine.domain.quiz.dto.*;
import com.example.WebQuizEngine.domain.quiz.exception.DeleteQuizForbiddenException;
import com.example.WebQuizEngine.domain.quiz.exception.QuizItemNotExistException;
import com.example.WebQuizEngine.domain.quiz.exception.errorMessage.ErrorMessages;
import com.example.WebQuizEngine.domain.quiz.mapper.QuizMapper;
import com.example.WebQuizEngine.domain.quiz.models.CompletedQuizHistoryEntity;
import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;
import com.example.WebQuizEngine.domain.quiz.repository.QuizHistoryRepository;
import com.example.WebQuizEngine.domain.quiz.repository.QuizRepository;
import com.example.WebQuizEngine.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Service
public class QuizService {

    private final UserService userService;
    private final QuizRepository quizRepository;
    private final QuizHistoryRepository quizHistoryRepository;

    public QuizService(
            UserService userService,
            QuizRepository quizRepository,
            QuizHistoryRepository quizHistoryRepository
    ) {
        this.userService = userService;
        this.quizRepository = quizRepository;
        this.quizHistoryRepository = quizHistoryRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createNewQuiz(UUID userId, @Valid CreateQuizRequestDTO createQuizRequestDTO) {
        userService.userWithIDExists(userId);
        QuizEntity quizEntity = QuizMapper
                .convertCreateQuizRequestDTOToQuizEntity(
                        userId,
                        createQuizRequestDTO
                );
        quizRepository.save(quizEntity);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public QuizResponseDTO getQuiz(UUID userId, UUID quizId) {
        userService.userWithIDExists(userId);
        QuizEntity quizEntity = quizRepository
                .findSingleQuizItemWithQuizIdAndUserId(quizId, userId);
        if (quizEntity == null) {
            throw new QuizItemNotExistException(ErrorMessages.QUIZ_NOT_EXIST_EXCEPTION);
        }
        return QuizMapper.convertQuizEntityToQuizResponseDTO(quizEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public QuizAnswerResultDTO answerQuiz(
            UUID userId,
            UUID quizId,
            QuizAnswerSubmitDTO quizAnswerSubmitDTO) {
        userService.userWithIDExists(userId);

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
        if (quizAnswerResultDTO.getSuccess() == true) {
            CompletedQuizHistoryEntity completedQuizHistoryEntity
                    = new CompletedQuizHistoryEntity(
                    userId,
                    quizId,
                    Timestamp.from(Instant.now()),
                    "finish");
            insertQuizCompleteHistoryRecord(completedQuizHistoryEntity);
        }

        return quizAnswerResultDTO;
    }

    public void insertQuizCompleteHistoryRecord(CompletedQuizHistoryEntity completedQuizHistoryEntity) {
        quizHistoryRepository.save(completedQuizHistoryEntity);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Page<QuizResponseDTO> getQuizzes(UUID userId, int page) {
        userService.userWithIDExists(userId);
        Pageable quizzesPageable = PageRequest.of(page, 10);
        Page<QuizEntity> quizEntityPage = quizRepository.findQuizzesQueryNative(userId, quizzesPageable);
        return quizEntityPage.map(QuizMapper::convertQuizEntityToQuizResponseDTO);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Page<CompletedQuizHistoryResponseDTO> getCompletedQuizzes(UUID userId, int page) {
        userService.userWithIDExists(userId);
        Sort sortByDateDesc = Sort.by("finishTime").descending();
        Pageable quizzesPageable = PageRequest.of(page, 10, sortByDateDesc);
        Page<CompletedQuizHistoryEntity> completedQuizHistoryEntityPage
                = quizHistoryRepository.findCompletedQuizHistoryQueryNative(userId, quizzesPageable);
        return completedQuizHistoryEntityPage
                .map(QuizMapper::convertCompletedQuizHistoryEntityToCompletedQuizHistoryResponseDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteQuiz(UUID userId, UUID quizOwnerId, UUID quizId) {
        userService.userWithIDExists(quizOwnerId);
        userService.userWithIDExists(userId);
        QuizEntity quizEntity = quizRepository.findSingleQuizItemWithQuizId(quizId);
        if (quizEntity == null) {
            throw new QuizItemNotExistException(ErrorMessages.QUIZ_NOT_EXIST_EXCEPTION);
        }
        if (!(quizEntity.getUserId().equals(userId))) {
            throw new DeleteQuizForbiddenException(ErrorMessages.FORBID_DELETE_QUIZ_EXCEPTION);
        }
        quizRepository.delete(quizEntity);
    }
}
