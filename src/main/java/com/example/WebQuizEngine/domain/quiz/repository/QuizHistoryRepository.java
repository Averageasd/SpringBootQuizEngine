package com.example.WebQuizEngine.domain.quiz.repository;

import com.example.WebQuizEngine.domain.quiz.models.CompletedQuizHistoryEntity;
import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizHistoryRepository extends JpaRepository<CompletedQuizHistoryEntity, UUID> {

    @Query(value = "SELECT * FROM quizuserhistory quh WHERE quh.userid = :userId", nativeQuery = true)
    Page<CompletedQuizHistoryEntity> findCompletedQuizHistoryQueryNative(@Param("userId") UUID userId, Pageable pageable);
}
