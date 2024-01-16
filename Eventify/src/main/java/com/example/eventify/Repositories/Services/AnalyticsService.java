package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.AnalyticsResponse;
import com.example.eventify.Entities.Event;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnalyticsService {
    private final IEventRepository _EventRepository;

    @Autowired
    public AnalyticsService(IEventRepository _EventRepository) {
        this._EventRepository = _EventRepository;
    }

    public ResponseEntity<ApiResponse<AnalyticsResponse>> GetEventAnalytics(Long eventId){
        Optional<Event> event = _EventRepository.findById(eventId);

        if (event.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        AnalyticsResponse response = new AnalyticsResponse();
        response.setNrOfAttendees(event.get().getRegistrations().size());
        response.setEventName(event.get().getEventName());
        response.setVenue(event.get().getVenue());
        response.setSpeakers(event.get().getSpeakers());

        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
}
