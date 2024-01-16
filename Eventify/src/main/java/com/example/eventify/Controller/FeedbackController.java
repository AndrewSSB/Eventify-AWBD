package com.example.eventify.Controller;

import com.example.eventify.DTO.Feedback.CreateFeedbackModel;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> AddSpeaker(@RequestBody CreateFeedbackModel model){
        return feedbackService.AddFeedback(model);
    }
}
