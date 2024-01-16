package com.example.eventify.Kernel.Validations;

import com.example.eventify.Kernel.Constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RatingConstraint {
    String message() default Constants.INVALID_RATING;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
