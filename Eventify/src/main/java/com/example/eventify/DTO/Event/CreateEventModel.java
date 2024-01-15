package com.example.eventify.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CreateEventModel {
    private String EventName;
    private Date EventDate;
    private String Description;
    private Long VenueId;
    private List<Long> TagIds;
    private List<Long> SpeakerIds;
    private Long OrganizerId;
}
