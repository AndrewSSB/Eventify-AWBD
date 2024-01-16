package com.example.eventify.DTO.Speaker;

import com.example.eventify.DTO.Auth.Login.LoginModel;

import java.util.ArrayList;
import java.util.List;

public class CreateSpeakerValidator {
    public static List<String> Validate(CreateSpeakerModel model){
        List<String> errors = new ArrayList<>();

        ValidateFieldNotNull(model.getSpeakerName(), "SpeakerName", errors);
        ValidateFieldNotNull(model.getBio(), "Bio", errors);

        return errors;
    }

    private static void ValidateFieldNotNull(String value, String fieldName, List<String> errors) {
        if (value == null) {
            errors.add(fieldName + " cannot be null.");
        }
    }
}
