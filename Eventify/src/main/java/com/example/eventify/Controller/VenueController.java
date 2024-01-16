package com.example.eventify.Controller;


import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Venue.CreateVenueModel;
import com.example.eventify.DTO.Venue.EditVenueModel;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Repositories.Services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venue")
public class VenueController {
    private final VenueService _venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        _venueService = venueService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> CreateVenue(@RequestBody CreateVenueModel model){
        return _venueService.CreateVenue(model);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<VenueResponse>>> GetAllVenues(){
        return _venueService.GetAllVenues();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<VenueResponse>> GetVenueById(@PathVariable Long id){
        return _venueService.GetById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<String>> DeleteVenue(@PathVariable Long id){
        return _venueService.DeleteVenue(id);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<VenueResponse>> DeleteEvent(@RequestBody EditVenueModel model){
        return _venueService.EditVenue(model);
    }
}
