package com.example.eventify.Controller;

import com.example.eventify.DTO.AnalyticsResponse;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService _analyticsService;

    @Autowired
    public AnalyticsController(AnalyticsService _analyticsService) {
        this._analyticsService = _analyticsService;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<AnalyticsResponse>> GetEventAnalytics(@PathVariable Long id){
        return _analyticsService.GetEventAnalytics(id);
    }
}
