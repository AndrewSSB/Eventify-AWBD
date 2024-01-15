package com.example.eventify.DTO.Event;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EditEventModel {
    private Long Id;
    @Nullable
    private String EventName;
    @Nullable
    private Date EventDate;
    @Nullable
    private String Description;
    @Nullable
    private Long VenueId;
    @Nullable
    private List<Long> TagIds;
    @Nullable
    private List<Long> SpeakerIds;
    @Nullable
    private Long OrganizerId;
}
