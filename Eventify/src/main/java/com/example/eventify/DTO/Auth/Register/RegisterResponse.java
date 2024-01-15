package com.example.eventify.DTO.Auth.Register;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponse {
    private String AccessToken;

    public RegisterResponse(String accessToken){
        AccessToken = accessToken;
    }

    public RegisterResponse() {
    }
}
