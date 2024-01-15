package com.example.eventify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Registrations")
public class Registration extends BaseEntity {
    @Column(name = "RegistrationDate")
    private LocalDateTime RegistrationDate;
    @Column(name = "Status")
    private String Status;

    @ManyToOne
    private User User;

    @ManyToOne
    private Event Event;
}
