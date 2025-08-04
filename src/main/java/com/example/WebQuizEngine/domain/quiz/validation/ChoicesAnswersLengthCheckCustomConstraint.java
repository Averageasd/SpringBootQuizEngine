package com.example.WebQuizEngine.domain.quiz.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ChoicesAnswersLengthCheckValidator.class)
@Documented
public @interface ChoicesAnswersLengthCheckCustomConstraint {
    String message() default "Fields do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String choices();
    String answers();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ChoicesAnswersLengthCheckCustomConstraint[] value();
    }

}
