package com.example.eventify.DTO.User;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String Username;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
}
