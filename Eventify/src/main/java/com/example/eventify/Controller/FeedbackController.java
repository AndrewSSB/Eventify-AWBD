package com.example.eventify.Controller;

import com.example.eventify.DTO.Event.EditEventModel;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.DTO.Feedback.CreateFeedbackModel;
import com.example.eventify.DTO.Feedback.EditFeedbackModel;
import com.example.eventify.DTO.Feedback.FeedBackResponse;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<String>> AddFeedback(@RequestBody CreateFeedbackModel model){
        return feedbackService.AddFeedback(model);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<FeedBackResponse>>> GetAllFeedbacks(){
        return feedbackService.GetFeedbacks();
    }
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<FeedBackResponse>> GetFeedbackById(@PathVariable Long id){
        return feedbackService.GetFeedbackById(id);
    }

    @ResponseBody
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> DeleteFeedback(@PathVariable Long id){
        return feedbackService.DeleteFeedback(id);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<FeedBackResponse>> EditFeedback(@RequestBody EditFeedbackModel model){
        return feedbackService.EditFeedback(model);
    }
}
