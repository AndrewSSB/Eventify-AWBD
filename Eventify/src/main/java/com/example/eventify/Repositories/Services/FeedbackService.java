package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.Feedback.CreateFeedbackModel;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Feedback;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import com.example.eventify.Repositories.Interfaces.IFeedbackRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {
    private final IFeedbackRepository _feedbackRepository;
    private final IUserRepository _userRepository;
    private final IEventRepository _eventRepository;

    @Autowired
    public FeedbackService(IFeedbackRepository _feedbackRepository, IUserRepository _userRepository, IEventRepository _eventRepository) {
        this._feedbackRepository = _feedbackRepository;
        this._userRepository = _userRepository;
        this._eventRepository = _eventRepository;
    }

    public ResponseEntity<ApiResponse<String>> AddFeedback(CreateFeedbackModel model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        Optional<User> user = _userRepository.findByUsername(username);

        if (user.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidUser), HttpStatus.BAD_REQUEST);
        }

        Optional<Event> eventOptional = _eventRepository.findById(model.getEventId());
        if (eventOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        Feedback feedback = new Feedback();
        feedback.setComment(model.getComment());
        feedback.setRating(model.getRating());
        feedback.setUser(user.get());
        feedback.setEvent(eventOptional.get());

        _feedbackRepository.save(feedback);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }
}

