package com.example.eventify.Repositories.Services;

import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Venue.CreateVenueModel;
import com.example.eventify.DTO.Venue.EditVenueModel;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Entities.Venue;
import com.example.eventify.Repositories.Interfaces.IVenueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {

    private final IVenueRepository _venueRepository;
    private final ModelMapper _mapper;

    @Autowired
    public VenueService(IVenueRepository venueRepository, ModelMapper mapper){
        _venueRepository = venueRepository;
        _mapper = mapper;
    }

    public ResponseEntity<ApiResponse<String>> CreateVenue(CreateVenueModel model){
        // validator

        Venue venue = _mapper.map(model, Venue.class);

        _venueRepository.save(venue);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<VenueResponse>> EditVenue(EditVenueModel model) {
        Optional<Venue> optionalVenue = _venueRepository.findById(model.getId());

        if (optionalVenue.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>("There is no venue with the specified Id"), HttpStatus.BAD_REQUEST);
        }

        Venue venue = optionalVenue.get();

        if (model.getVenueName() != null){
            venue.setVenueName(model.getVenueName());
        }

        if (model.getLocation() != null) {
            venue.setLocation(model.getLocation());
        }

        if (model.getCapacity() != null){
            venue.setCapacity(model.getCapacity());
        }

        _venueRepository.save(venue);

        return new ResponseEntity<>(new ApiResponse<>(_mapper.map(venue, VenueResponse.class)), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<String>> DeleteVenue(Long Id) {

        Optional<Venue> venue = _venueRepository.findById(Id);

        if (venue.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>("There is no venue with the specified Id"), HttpStatus.BAD_REQUEST);
        }

        _venueRepository.delete(venue.get());

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<VenueResponse>>> GetAllVenues(){

        List<Venue> venues = _venueRepository.findAll();

        List<VenueResponse> response = venues.stream()
                .map(event -> _mapper.map(event, VenueResponse.class))
                .toList();

        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<VenueResponse>> GetById(Long Id){
        Optional<Venue> venue = _venueRepository.findById(Id);

        if (venue.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>("There is no venue with the specified Id"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse<>(_mapper.map(venue.get(), VenueResponse.class)), HttpStatus.OK);
    }
}
