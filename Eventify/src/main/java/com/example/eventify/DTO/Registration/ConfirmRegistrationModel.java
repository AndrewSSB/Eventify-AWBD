package com.example.eventify.DTO.Registration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConfirmRegistrationModel {
    private Long RegistrationId;
    private Long EventId;
    private Boolean ConfirmAll;
}
