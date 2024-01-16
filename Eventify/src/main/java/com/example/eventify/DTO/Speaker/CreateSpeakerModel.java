package com.example.eventify.DTO.Speaker;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSpeakerModel {
    private String SpeakerName;
    private String Bio;
}
