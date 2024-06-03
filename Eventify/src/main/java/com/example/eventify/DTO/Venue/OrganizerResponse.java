package com.example.eventify.DTO.Venue;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrganizerResponse {
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
}
