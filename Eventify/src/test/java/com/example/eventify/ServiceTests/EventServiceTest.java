package com.example.eventify.ServiceTests;

import com.example.eventify.DTO.ApiResponse.ApiResponse;
import com.example.eventify.DTO.CreateEventModel;
import com.example.eventify.DTO.Event.EditEventModel;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.DTO.Venue.EditVenueModel;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Entities.*;
import com.example.eventify.Repositories.Interfaces.*;
import com.example.eventify.Repositories.Services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @Mock
    private IEventRepository _eventRepository;
    @Mock
    private IVenueRepository _venueRepository;
    @Mock
    private ITagRepository _tagRepository;
    @Mock
    private ISpeakerRepository _speakerRepository;
    @Mock
    private IUserRepository _userRepository;
    @Spy
    private ModelMapper _mapper;

    @InjectMocks
    private EventService _eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent(){
        CreateEventModel eventModel = new CreateEventModel();
        eventModel.setEventName("Test");
        eventModel.setEventDate(new Date());
        eventModel.setDescription("Description");
        eventModel.setTagIds(List.of(1L));
        eventModel.setSpeakerIds(List.of(1L));
        eventModel.setVenueId(1L);
        eventModel.setOrganizerId(1L);

        when(_venueRepository.findById(any())).thenReturn(Optional.of(new Venue()));
        when(_tagRepository.findAllById(any())).thenReturn(List.of(new Tag()));
        when(_speakerRepository.findAllById(any())).thenReturn(List.of(new Speaker()));
        when(_userRepository.findById(any())).thenReturn(Optional.of(new User()));

        ResponseEntity<ApiResponse<String>> responseEntity = _eventService.CreateEvent(eventModel);

        verify(_venueRepository, times(1)).findById(any());
        verify(_tagRepository, times(1)).findAllById(any());
        verify(_speakerRepository, times(1)).findAllById(any());
        verify(_userRepository, times(1)).findById(any());

        verify(_eventRepository, times(1)).save(any());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteEventById(){
        Long eventId = 1L;

        Optional<Event> optionalEvent = Optional.of(new Event());
        when(_eventRepository.findById(eventId)).thenReturn(optionalEvent);

        ResponseEntity<ApiResponse<String>> responseEntity = _eventService.DeleteEvent(eventId);

        verify(_eventRepository, times(1)).findById(eventId);
        verify(_eventRepository, times(1)).delete(optionalEvent.get());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetEventById(){
        Long eventId = 1L;
        Event event = new Event();
        EventResponse mappedEventResponse = new EventResponse();

        when(_eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(_mapper.map(event, EventResponse.class)).thenReturn(mappedEventResponse);

        ResponseEntity<ApiResponse<EventResponse>> responseEntity = _eventService.GetEventById(eventId);

        verify(_eventRepository, times(1)).findById(eventId);
        verify(_mapper, times(1)).map(event, EventResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedEventResponse, responseEntity.getBody().getResponseData());
    }

    @Test
    void testGetAll(){
        List<Event> events = List.of(new Event());
        List<EventResponse> mappedEvents = List.of(new EventResponse());

        when(_eventRepository.findAll()).thenReturn(events);
        when(_mapper.map(events.get(0), EventResponse.class)).thenReturn(mappedEvents.get(0));

        ResponseEntity<ApiResponse<List<EventResponse>>> responseEntity = _eventService.GetAllEvent();

        verify(_eventRepository, times(1)).findAll();
        verify(_mapper, times(1)).map(any(), eq(EventResponse.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedEvents, responseEntity.getBody().getResponseData());
    }

    @Test
    void testEditEvent(){
        Long eventId = 1L;
        Event event = new Event();
        EventResponse mappedEvent = new EventResponse();
        EditEventModel editEventModel = new EditEventModel();
        editEventModel.setId(1L);

        when(_eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(_mapper.map(event, EventResponse.class)).thenReturn(mappedEvent);

        ResponseEntity<ApiResponse<EventResponse>> responseEntity = _eventService.EditEvent(editEventModel);

        verify(_eventRepository, times(1)).findById(eventId);
        verify(_mapper, times(1)).map(event, EventResponse.class);
        verify(_eventRepository, times(1)).save(event);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedEvent, responseEntity.getBody().getResponseData());
    }
}
