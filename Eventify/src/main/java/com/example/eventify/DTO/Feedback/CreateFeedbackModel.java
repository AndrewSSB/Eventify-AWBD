package com.example.eventify.DTO.Feedback;

import com.example.eventify.Kernel.Validations.RatingConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateFeedbackModel {
    @RatingConstraint
    private int Rating;
    private String Comment;
    private Long EventId;
}
