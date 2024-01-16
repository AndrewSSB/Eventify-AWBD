package com.example.eventify.ServiceTests;

import com.example.eventify.DTO.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Venue.CreateVenueModel;
import com.example.eventify.DTO.Venue.EditVenueModel;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Entities.Venue;
import com.example.eventify.Repositories.Interfaces.IVenueRepository;
import com.example.eventify.Repositories.Services.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VenueServiceTest {
    @Mock
    private IVenueRepository _venueRepository;
    @Spy
    private ModelMapper _mapper;
    @InjectMocks
    private VenueService _venueService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVenue(){
        CreateVenueModel createVenueModel = new CreateVenueModel();
        Venue mappedVenue = new Venue();

        when(_mapper.map(createVenueModel, Venue.class)).thenReturn(mappedVenue);
        when(_venueRepository.save(mappedVenue)).thenReturn(mappedVenue);

        ResponseEntity<ApiResponse<String>> responseEntity = _venueService.CreateVenue(createVenueModel);

        verify(_mapper, times(1)).map(createVenueModel, Venue.class);
        verify(_venueRepository, times(1)).save(mappedVenue);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteVenueById(){
        Long venueId = 1L;

        Optional<Venue> optionalVenue = Optional.of(new Venue());
        when(_venueRepository.findById(venueId)).thenReturn(optionalVenue);

        ResponseEntity<ApiResponse<String>> responseEntity = _venueService.DeleteVenue(venueId);

        verify(_venueRepository, times(1)).findById(venueId);
        verify(_venueRepository, times(1)).delete(optionalVenue.get());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetById(){
        Long venueId = 1L;
        Venue venue = new Venue();
        VenueResponse mappedVenueResponse = new VenueResponse();

        when(_venueRepository.findById(venueId)).thenReturn(Optional.of(venue));
        when(_mapper.map(venue, VenueResponse.class)).thenReturn(mappedVenueResponse);

        ResponseEntity<ApiResponse<VenueResponse>> responseEntity = _venueService.GetById(venueId);

        verify(_venueRepository, times(1)).findById(venueId);
        verify(_mapper, times(1)).map(venue, VenueResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedVenueResponse, responseEntity.getBody().getResponseData());
    }

    @Test
    void testGetAll(){
        List<Venue> venues = List.of(new Venue());
        List<VenueResponse> mappedVenues = List.of(new VenueResponse());

        when(_venueRepository.findAll()).thenReturn(venues);
        when(_mapper.map(venues.get(0), VenueResponse.class)).thenReturn(mappedVenues.get(0));

        ResponseEntity<ApiResponse<List<VenueResponse>>> responseEntity = _venueService.GetAllVenues();

        verify(_venueRepository, times(1)).findAll();
        verify(_mapper, times(1)).map(any(), eq(VenueResponse.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedVenues, responseEntity.getBody().getResponseData());
    }

    @Test
    void testEditVenue(){
        Long venueId = 1L;
        Venue venue = new Venue();
        VenueResponse mappedVenue = new VenueResponse();
        EditVenueModel editVenueModel = new EditVenueModel();
        editVenueModel.setId(1L);

        when(_venueRepository.findById(venueId)).thenReturn(Optional.of(venue));
        when(_mapper.map(venue, VenueResponse.class)).thenReturn(mappedVenue);

        ResponseEntity<ApiResponse<VenueResponse>> responseEntity = _venueService.EditVenue(editVenueModel);

        verify(_venueRepository, times(1)).findById(venueId);
        verify(_mapper, times(1)).map(venue, VenueResponse.class);
        verify(_venueRepository, times(1)).save(venue);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mappedVenue, responseEntity.getBody().getResponseData());
    }
}
