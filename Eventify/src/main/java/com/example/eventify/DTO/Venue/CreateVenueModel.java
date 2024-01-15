package com.example.eventify.DTO.Venue;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateVenueModel {
    private String VenueName;
    private int Capacity;
    private String Location;
}
