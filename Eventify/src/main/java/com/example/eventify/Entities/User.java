package com.example.eventify.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User extends BaseEntity {

    @Column(name = "Username")
    private String Username;
    @Column(name = "Password")
    private String Password;
    @Column(name = "FirstName")
    private String FirstName;
    @Column(name = "LastName")
    private String LastName;
    @Column(name = "Email")
    private String Email;
    @Column(name = "PhoneNumber")
    private String PhoneNumber;

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        Username = username;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    (
        name = "UserRoles",
        joinColumns = @JoinColumn(name = "UserId", referencedColumnName = "Id"),
        inverseJoinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "Id")
    )
    private List<Role> Roles;

    public User() {

    }

    private static String GenerateSalt(){
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return Base64.encodeBase64String(salt);
    }
}
