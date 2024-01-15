package com.example.eventify.DTO.Event;

import com.example.eventify.Entities.*;

import java.util.Date;
import java.util.List;

public class EventResponse {
    private String EventName;
    private Date EventDate;
    private String Description;
    private Venue Venue;
    private List<Tag> Tags;
    private List<Speaker> Speakers;
    private List<Registration> Registrations;
    private List<Feedback> Feedbacks;
    private User Organizer;
}
