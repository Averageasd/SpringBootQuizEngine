package com.example.WebQuizEngine.domain.quiz.dto;

public class QuizAnswerResultDTO {
    public static final String SUCCESS_MESSAGE = "Congratulations, you're right!";
    public static final String FAIL_MESSAGE = "Wrong answer! Please, try again.";
    public Boolean success;
    public String feedback;

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
