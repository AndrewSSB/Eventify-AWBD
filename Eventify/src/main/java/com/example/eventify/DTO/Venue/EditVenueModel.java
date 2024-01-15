package com.example.eventify.DTO.Venue;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditVenueModel {
    private Long Id;
    @Nullable
    private String VenueName;
    @Nullable
    private Integer Capacity;
    @Nullable
    private String Location;
}
