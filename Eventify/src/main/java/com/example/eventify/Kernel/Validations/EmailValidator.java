package com.example.eventify.Kernel.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && email.contains("@");
    }
}
