package com.example.eventify.DTO.Feedback;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedBackResponse {
    private int Rating;
    private String Comment;
    private Long EventId;
}
