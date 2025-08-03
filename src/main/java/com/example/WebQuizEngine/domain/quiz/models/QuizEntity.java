package com.example.WebQuizEngine.domain.quiz.models;

import com.example.WebQuizEngine.util.CustomIntegerArrayType;
import com.example.WebQuizEngine.util.CustomStringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table(name = "quizitem")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid DEFAULT uuid_generate_v1()", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", length = 50, nullable = false)
    @Length(min = 1, max = 30, message = "Title length must be between 1 and 30 characters")
    private String title;

    @Column(name = "text", length = 250, nullable = false)
    @Length(min = 1, max = 250, message = "Text length must be between 1 and 250 characters")
    private String text;

    @Column(name = "choices", length = 4, nullable = false)
    @Type(value = CustomStringArrayType.class)
    @Size(min = 2, max = 4, message = "There must be at least 2 choices")
    private String[] choices;

    @Column(name = "answers")
    @Type(value = CustomIntegerArrayType.class)
    private Integer[] answers;

    public QuizEntity() {
    }

    public QuizEntity(String title, String text, String[] choices, Integer[] answers) {
        this.title = title;
        this.text = text;
        this.choices = choices;
        this.answers = answers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setAnswers(Integer[] answers) {
        this.answers = answers;
    }

    public Integer[] getAnswers() {
        return answers;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }
}
