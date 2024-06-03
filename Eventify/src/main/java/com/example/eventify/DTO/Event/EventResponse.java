package com.example.eventify.DTO.Event;

import com.example.eventify.DTO.Venue.OrganizerResponse;
import com.example.eventify.Entities.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class EventResponse {
    private Long Id;
    private String EventName;
    private Date EventDate;
    private String Description;
    private Venue Venue;
    private List<Tag> Tags;
    private List<Speaker> Speakers;
    private List<Registration> Registrations;
    private List<Feedback> Feedbacks;
    private OrganizerResponse Organizer;
}
