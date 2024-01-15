package com.example.eventify.DTO.Auth.Login;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String AccessToken;

    public LoginResponse(String accessToken) {
        AccessToken = accessToken;
    }

    public LoginResponse() {
    }
}
