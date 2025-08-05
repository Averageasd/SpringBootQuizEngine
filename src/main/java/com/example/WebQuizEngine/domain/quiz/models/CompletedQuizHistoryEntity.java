package com.example.WebQuizEngine.domain.quiz.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "quizUserHistory")
public class CompletedQuizHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT uuid_generate_v1()", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "userId", columnDefinition = "uuid DEFAULT uuid_generate_v1()", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "quizId", columnDefinition = "uuid DEFAULT uuid_generate_v1()", updatable = false, nullable = false)
    private UUID quizId;

    @Column(name = "finishTime", nullable = false)
    private Timestamp finishTime;

    @Column(name = "status", nullable = false)
    private String status;

    public CompletedQuizHistoryEntity(){}

    public CompletedQuizHistoryEntity(UUID userId, UUID quizId, Timestamp finishTime, String status) {
        this.userId = userId;
        this.quizId = quizId;
        this.finishTime = finishTime;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getQuizId() {
        return quizId;
    }

    public void setQuizId(UUID quizId) {
        this.quizId = quizId;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
