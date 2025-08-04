package com.example.WebQuizEngine.domain.quiz.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ChoicesAnswersLengthCheckValidator implements ConstraintValidator<ChoicesAnswersLengthCheckCustomConstraint, Object> {

    private String choicesField;
    private String answersField;

    @Override
    public void initialize(ChoicesAnswersLengthCheckCustomConstraint constraintAnnotation) {
        choicesField = constraintAnnotation.choices();
        answersField = constraintAnnotation.answers();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        constraintValidatorContext.disableDefaultConstraintViolation();
        BeanWrapper bw = new BeanWrapperImpl(o);
        String[] choices = (String[])bw.getPropertyValue(choicesField);
        Integer[] answers = (Integer[])bw.getPropertyValue(answersField);

        if (answers.length > choices.length){
            isValid = false;
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("answers length cannot be greater than choices length")
                    .addPropertyNode("answers")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
