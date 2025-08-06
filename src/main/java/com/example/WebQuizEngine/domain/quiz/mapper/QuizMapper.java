package com.example.WebQuizEngine.domain.quiz.mapper;

import com.example.WebQuizEngine.domain.quiz.dto.CompletedQuizHistoryResponseDTO;
import com.example.WebQuizEngine.domain.quiz.dto.CreateQuizRequestDTO;
import com.example.WebQuizEngine.domain.quiz.dto.QuizResponseDTO;
import com.example.WebQuizEngine.domain.quiz.models.CompletedQuizHistoryEntity;
import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class QuizMapper {

    public static QuizResponseDTO convertQuizEntityToQuizResponseDTO(QuizEntity quizEntity) {
        return new QuizResponseDTO(
                quizEntity.getId(),
                quizEntity.getTitle(),
                quizEntity.getText(),
                quizEntity.getChoices(),
                quizEntity.getAnswers()
        );
    }

    private static LocalDateTime convertQuizHistoryFinishTimeToLocalDateTime(Timestamp timestamp) {
        return timestamp.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static CompletedQuizHistoryResponseDTO convertCompletedQuizHistoryEntityToCompletedQuizHistoryResponseDTO(
            CompletedQuizHistoryEntity completedQuizHistoryEntity
    ) {
        return new CompletedQuizHistoryResponseDTO(
                completedQuizHistoryEntity.getId(),
                convertQuizHistoryFinishTimeToLocalDateTime(
                        completedQuizHistoryEntity.getFinishTime()
                )
        );
    }

    public static QuizEntity convertCreateQuizRequestDTOToQuizEntity(
            UUID userId,
            CreateQuizRequestDTO createQuizRequestDTO
    ) {
        return new QuizEntity(
                createQuizRequestDTO.title(),
                createQuizRequestDTO.text(),
                createQuizRequestDTO.choices(),
                createQuizRequestDTO.answers(),
                userId
        );
    }
}
