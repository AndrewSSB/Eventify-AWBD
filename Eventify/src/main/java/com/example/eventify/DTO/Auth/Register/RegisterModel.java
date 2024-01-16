package com.example.eventify.DTO.Auth.Register;

import com.example.eventify.Kernel.Validations.EmailConstraint;
import com.example.eventify.Kernel.Validations.PasswordConstraint;
import com.example.eventify.Kernel.Validations.PhoneNumberConstraint;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterModel {
    private String Username;
    @PasswordConstraint
    private String Password;
    private String FirstName;
    private String LastName;
    @EmailConstraint
    private String Email;
    @PhoneNumberConstraint
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
