package com.example.WebQuizEngine.domain.quiz.repository;

import com.example.WebQuizEngine.domain.quiz.models.QuizEntity;
import com.example.WebQuizEngine.domain.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, UUID> {

    @Query(value = "SELECT * FROM quizitem qz WHERE qz.id = :quizId AND qz.userid = :userId", nativeQuery = true)
    QuizEntity findSingleQuizItemWithQuizIdAndUserId(@Param("quizId") UUID quizId, @Param("userId") UUID userId);
}
