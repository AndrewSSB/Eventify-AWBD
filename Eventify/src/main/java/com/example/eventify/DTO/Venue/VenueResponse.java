package com.example.eventify.DTO.Venue;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VenueResponse {
    private Long Id;
    private String VenueName;
    private int Capacity;
    private String Location;
}
