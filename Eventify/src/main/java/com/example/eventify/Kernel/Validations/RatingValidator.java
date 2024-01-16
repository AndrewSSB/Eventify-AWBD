package com.example.eventify.Kernel.Validations;

import com.example.eventify.Kernel.Constants.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<RatingConstraint, Integer> {
    @Override
    public boolean isValid(Integer rating, ConstraintValidatorContext context) {
        return 0 <= rating && rating <= 5;
    }
}
