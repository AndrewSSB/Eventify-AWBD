package com.example.eventify.ServiceTests;
import com.example.eventify.DTO.Registration.CreateRegistrationModel;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Registration;
import com.example.eventify.Entities.User;
import com.example.eventify.Entities.Venue;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import com.example.eventify.Repositories.Interfaces.IRegistrationRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import com.example.eventify.Repositories.Services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class RegistrationServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRegistration() {
        CreateRegistrationModel model = new CreateRegistrationModel();
        model.setEventId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn("testUser");

        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Event event = new Event();
        event.setId(1L);
        Venue venue = new Venue();
        venue.setCapacity(10);
        event.setVenue(venue);
        when(eventRepository.findById(eq(1L))).thenReturn(Optional.of(event));

        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        ResponseEntity<ApiResponse<String>> responseEntity = registrationService.CreateRegistration(model);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateRegistration_RegistrationLimitReached() {
        CreateRegistrationModel model = new CreateRegistrationModel();
        model.setEventId(1L);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn("testUser");

        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Event event = new Event();
        event.setId(1L);
        Venue venue = new Venue();
        venue.setCapacity(0);
        event.setVenue(venue);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        ResponseEntity<ApiResponse<String>> responseEntity = registrationService.CreateRegistration(model);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(eventRepository, times(1)).findById(1L);
    }
}
