package com.example.WebQuizEngine.user.models;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "quizuser")
public class UserEntity {

    // we can use @Size and @Length interchangeably @Size is java related while the other is Hibernate-specific
    // Column is to specify db column properties, it does not provide additional validation
    // column definition. we use this to determine how to define columns.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT uuid_generate_v1()", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "userName", length = 30, nullable = false, unique = true)
    @Length(min = 3, max = 30, message = "Name length must be between 3 and 30 characters")
    private String userName;

    @Column(name = "userPassword", length = 50, nullable = false)
    @Length(min = 8, max = 50, message = "Password length must be between 8 and 50 characters")
    private String userPassword;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String userPasswordHash;

    public UserEntity(String userName, String userPassword, String userPasswordHash) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPasswordHash = userPasswordHash;
    }

    public UserEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }
}
