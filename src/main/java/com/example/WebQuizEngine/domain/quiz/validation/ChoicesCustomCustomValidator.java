package com.example.WebQuizEngine.domain.quiz.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ChoicesCustomCustomValidator implements ConstraintValidator<ChoicesCustomConstraint, String[]> {

    @Override
    public boolean isValid(String[] choices, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        for (String choice : choices) {
            if (choice.isEmpty() || choice.length() > 50) {
                isValid = false;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("One of the choices has incorrect length. Length of choice must be between 1 and 50")
                        .addPropertyNode("choices")
                        .addConstraintViolation();
            }
        }
        return isValid;
    }
}
