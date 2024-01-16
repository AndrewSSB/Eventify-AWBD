package com.example.eventify.DTO;

import com.example.eventify.Entities.Speaker;
import com.example.eventify.Entities.Venue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AnalyticsResponse {
    private String EventName;
    private Integer NrOfAttendees;
    private List<Speaker> Speakers;
    private Venue Venue;
}
