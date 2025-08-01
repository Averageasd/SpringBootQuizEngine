package com.example.WebQuizEngine.user.repository;

import com.example.WebQuizEngine.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM quizuser qu WHERE qu.userName = :userName", nativeQuery = true)
    UserEntity findByNameQueryNative(@Param("userName") String userName);
}
