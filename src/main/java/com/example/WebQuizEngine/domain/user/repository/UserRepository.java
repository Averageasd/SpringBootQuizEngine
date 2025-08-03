package com.example.WebQuizEngine.domain.user.repository;

import com.example.WebQuizEngine.domain.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT qu.id, qu.username,qu.userpassword, qu.userpasswordhash FROM quizuser qu WHERE qu.userName = :userName", nativeQuery = true)
    UserEntity findByNameQueryNative(@Param("userName") String userName);
}
