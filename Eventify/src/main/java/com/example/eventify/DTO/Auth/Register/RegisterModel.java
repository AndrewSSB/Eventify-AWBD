package com.example.eventify.DTO.Auth.Register;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterModel {
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;

    public RegisterModel(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        Username = username;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
    }

    public  RegisterModel(){

    }
}
