package com.example.eventify.DTO.Auth.Login;

import com.example.eventify.DTO.Auth.Register.RegisterModel;

import java.util.ArrayList;
import java.util.List;

public class LoginModelValidator {
    public static List<String> Validate(LoginModel model){
        List<String> errors = new ArrayList<>();

        ValidateFieldNotNull(model.getPassword(), "Password", errors);

        return errors;
    }

    private static void ValidateFieldNotNull(String value, String fieldName, List<String> errors) {
        if (value == null) {
            errors.add(fieldName + " cannot be null.");
        }
    }
}
