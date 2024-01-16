package com.example.eventify.ServiceTests;

import com.example.eventify.DTO.AnalyticsResponse;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Registration;
import com.example.eventify.Entities.Speaker;
import com.example.eventify.Entities.Venue;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import com.example.eventify.Repositories.Services.AnalyticsService;
import com.example.eventify.Repositories.Services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnalyticsServiceTest {
    @Mock
    private IEventRepository eventRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEventAnalytics() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setEventName("Test Event");
        event.setVenue(new Venue());
        List<Speaker> speakers = Arrays.asList(new Speaker(), new Speaker());
        event.setSpeakers(speakers);

        Registration registration1 = new Registration();
        Registration registration2 = new Registration();
        event.setRegistrations(Arrays.asList(registration1, registration2));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        ResponseEntity<ApiResponse<AnalyticsResponse>> response = analyticsService.GetEventAnalytics(eventId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Event", response.getBody().getResponseData().getEventName());
//        assertEquals(new Venue(), response.getBody().getResponseData().getVenue());
        assertEquals(2, response.getBody().getResponseData().getNrOfAttendees());
        assertEquals(speakers, response.getBody().getResponseData().getSpeakers());

        // Verify that findById was called with the correct eventId
        verify(eventRepository, times(1)).findById(eventId);
    }
}
