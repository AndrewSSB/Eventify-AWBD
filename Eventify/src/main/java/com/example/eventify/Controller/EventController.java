package com.example.eventify.Controller;

import com.example.eventify.DTO.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Event.EditEventModel;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.DTO.CreateEventModel;
import com.example.eventify.Repositories.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService _eventService;

    @Autowired
    public EventController(EventService eventService){
        _eventService = eventService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<EventResponse>>> GetAllEvents(){
        return _eventService.GetAllEvent();
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> CreateEvent(@RequestBody CreateEventModel model){
        return _eventService.CreateEvent(model);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<EventResponse>> GetEventById(@PathVariable Long id){
        return _eventService.GetEventById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<String>> DeleteEvent(@PathVariable Long id){
        return _eventService.DeleteEvent(id);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<EventResponse>> EditEvent(@RequestBody EditEventModel model){
        return _eventService.EditEvent(model);
    }
}
