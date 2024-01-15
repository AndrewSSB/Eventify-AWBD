package com.example.eventify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Venues")
public class Venue extends BaseEntity {
    @Column(name = "VenueName")
    private String VenueName;
    @Column(name = "Capacity")
    private int Capacity;
    @Column(name = "Location")
    private String Location;
}
