package com.example.eventify.Repositories.Services;

import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Event.CreateEventModelValidator;
import com.example.eventify.DTO.Event.EditEventModel;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.DTO.CreateEventModel;
import com.example.eventify.Entities.*;
import com.example.eventify.Repositories.Interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final IEventRepository _eventRepository;
    private final IVenueRepository _venueRepository;
    private final ITagRepository _tagRepository;
    private final ISpeakerRepository _speakerRepository;
    private final IUserRepository _userRepository;
    private final ModelMapper _mapper;

    @Autowired
    public EventService(IEventRepository eventRepository, IVenueRepository venueRepository, ITagRepository tagRepository, ISpeakerRepository speakerRepository, IUserRepository userRepository, ModelMapper mapper) {
        _eventRepository = eventRepository;
        _venueRepository = venueRepository;
        _tagRepository = tagRepository;
        _speakerRepository = speakerRepository;
        _userRepository = userRepository;
        _mapper = mapper;
    }

    public ResponseEntity<ApiResponse<List<EventResponse>>> GetAllEvent(){
        List<Event> events = _eventRepository.findAll();

        List<EventResponse> response = events.stream()
                .map(event -> _mapper.map(event, EventResponse.class))
                .toList();

        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<String>> CreateEvent(CreateEventModel model){
        List<String> validationErrors = CreateEventModelValidator.Validate(model);
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(validationErrors), HttpStatus.BAD_REQUEST);
        }

        Event event = new Event(model.getEventName(), model.getEventDate(), model.getDescription());

        // Set Venue
        Optional<Venue> venueOptional = _venueRepository.findById(model.getVenueId());
        if (venueOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidVenue), HttpStatus.BAD_REQUEST);
        }
        event.setVenue(venueOptional.get());

        // Set Tags
        List<Tag> tags = _tagRepository.findAllById(model.getTagIds());
        event.setTags(tags);

        // Set Speakers
        List<Speaker> speakers = _speakerRepository.findAllById(model.getSpeakerIds());
        event.setSpeakers(speakers);

        // Set Organizer
        Optional<User> organizerOptional = _userRepository.findById(model.getOrganizerId());
        if (organizerOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidVenue), HttpStatus.BAD_REQUEST);
        }
        event.setOrganizer(organizerOptional.get());

        // Save the event to the database
        _eventRepository.save(event);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<EventResponse>> GetEventById(Long id){
        Optional<Event> event = _eventRepository.findById(id);

        if (event.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse<>(_mapper.map(event.get(), EventResponse.class)), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<String>> DeleteEvent(Long id){
        Optional<Event> event = _eventRepository.findById(id);

        if (event.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        _eventRepository.delete(event.get());

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<EventResponse>> EditEvent(EditEventModel model){
        Optional<Event> optionalEvent = _eventRepository.findById(model.getId());

        if (optionalEvent.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        Event event = optionalEvent.get();

        if (model.getEventName() != null){
            event.setEventName(model.getEventName());
        }

        if (model.getEventDate() != null){
            event.setEventDate(model.getEventDate());
        }

        if (model.getDescription() != null){
            event.setEventName(model.getDescription());
        }

        if (model.getVenueId() != null){
            Optional<Venue> venueOptional = _venueRepository.findById(model.getVenueId());
            venueOptional.ifPresent(event::setVenue);
        }

        if (model.getTagIds() != null){
            List<Tag> tags = _tagRepository.findAllById(model.getTagIds());
            event.setTags(tags);
        }

        if (model.getSpeakerIds() != null) {
            List<Speaker> speakers = _speakerRepository.findAllById(model.getSpeakerIds());
            event.setSpeakers(speakers);
        }

        if (model.getOrganizerId() != null){
            Optional<User> organizerOptional = _userRepository.findById(model.getOrganizerId());
            organizerOptional.ifPresent(event::setOrganizer);
        }

        _eventRepository.save(event);
        return new ResponseEntity<>(new ApiResponse<EventResponse>(_mapper.map(event, EventResponse.class)), HttpStatus.OK);
    }
}
