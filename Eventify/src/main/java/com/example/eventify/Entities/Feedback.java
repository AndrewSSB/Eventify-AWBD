package com.example.eventify.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Feedbacks")
public class Feedback extends BaseEntity{
    @Column(name = "Rating")
    private int Rating;
    @Column(name = "Comment")
    private String Comment;

    @ManyToOne
    private Event Event;
    @ManyToOne
    private User User;
}
