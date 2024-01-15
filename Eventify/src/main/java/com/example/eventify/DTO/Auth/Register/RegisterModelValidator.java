package com.example.eventify.DTO.Auth.Register;

import java.util.ArrayList;
import java.util.List;

public class RegisterModelValidator {
    public static List<String> Validate(RegisterModel model){
        List<String> errors = new ArrayList<>();

        ValidateFieldNotNull(model.getEmail(), "Email", errors);
        ValidateFieldNotNull(model.getUsername(), "Username", errors);
        ValidateFieldNotNull(model.getPassword(), "Password", errors);
        ValidateFieldNotNull(model.getLastName(), "Last Name", errors);
        ValidateFieldNotNull(model.getFirstName(), "First Name", errors);
        ValidateFieldNotNull(model.getPhoneNumber(), "Phone Number", errors);

        return errors;
    }

    private static void ValidateFieldNotNull(String value, String fieldName, List<String> errors) {
        if (value == null) {
            errors.add(fieldName + " cannot be null.");
        }
    }
}
