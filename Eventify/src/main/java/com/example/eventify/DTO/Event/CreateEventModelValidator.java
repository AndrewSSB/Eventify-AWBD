package com.example.eventify.DTO.Event;

import com.example.eventify.DTO.CreateEventModel;

import java.util.ArrayList;
import java.util.List;

public class CreateEventModelValidator {
    public static List<String> Validate(CreateEventModel model){
        List<String> errors = new ArrayList<>();

        ValidateFieldNotNull(model.getEventName(), "EventName", errors);
        ValidateFieldNotNull(String.valueOf(model.getEventDate()), "EventDate", errors);

        return errors;
    }

    private static void ValidateFieldNotNull(String value, String fieldName, List<String> errors) {
        if (value == null) {
            errors.add(fieldName + " cannot be null.");
        }
    }
}
