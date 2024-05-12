package com.example.eventify.ServiceTests;

import com.example.eventify.DTO.Feedback.CreateFeedbackModel;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Feedback;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import com.example.eventify.Repositories.Interfaces.IFeedbackRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import com.example.eventify.Repositories.Services.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

public class FeedbackServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IFeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFeedback() {
        // Arrange
        CreateFeedbackModel model = new CreateFeedbackModel();
        model.setEventId(1L);
        model.setComment("Great event!");
        model.setRating(5);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn("testUser");

        User user = new User();
        user.setUsername("testUser1");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Event event = new Event();
        event.setId(1L);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // Act
        ResponseEntity<ApiResponse<String>> responseEntity = feedbackService.AddFeedback(model);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Verify that userRepository.findByUsername and eventRepository.findById were called
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(eventRepository, times(1)).findById(1L);

        // Verify that feedbackRepository.save was called with the correct feedback object
        ArgumentCaptor<Feedback> feedbackCaptor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository, times(1)).save(feedbackCaptor.capture());
        Feedback savedFeedback = feedbackCaptor.getValue();

        assertEquals("Great event!", savedFeedback.getComment());
        assertEquals(5, savedFeedback.getRating());
        assertEquals("testUser", savedFeedback.getUser().getUsername());
        assertEquals(1L, savedFeedback.getEvent().getId());
        assertEquals("userName1", user.getUsername());
    }
}
