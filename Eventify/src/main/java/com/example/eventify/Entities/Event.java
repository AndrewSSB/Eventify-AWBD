package com.example.eventify.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Events")
public class Event extends BaseEntity {
    @Column(name = "EventName")
    private String EventName;
    @Column(name = "EventDate")
    private Date EventDate;
    @Column(name = "Description")
    private String Description;

    public Event(){

    }

    public Event(String eventName, Date eventDate, String description) {
        EventName = eventName;
        EventDate = eventDate;
        Description = description;
    }

    @ManyToOne
    private Venue Venue;

    @ManyToMany
    private List<Tag> Tags;

    @ManyToMany
    private List<Speaker> Speakers;

    @OneToMany(mappedBy = "Event")
    private List<Registration> Registrations;

    @OneToMany(mappedBy = "Event")
    private List<Feedback> Feedbacks;

    @ManyToOne
    private User Organizer;
}
