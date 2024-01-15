package com.example.eventify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Speakers")
public class Speaker extends BaseEntity{
    @Column(name = "SpeakerName")
    private String SpeakerName;
    @Column(name = "Bio")
    private String Bio;
}
