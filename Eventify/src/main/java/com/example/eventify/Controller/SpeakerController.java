package com.example.eventify.Controller;

import com.example.eventify.DTO.Speaker.CreateSpeakerModel;
import com.example.eventify.DTO.Speaker.SpeakerResponse;
import com.example.eventify.DTO.Venue.CreateVenueModel;
import com.example.eventify.DTO.Venue.VenueResponse;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speaker")
public class SpeakerController {
    private final SpeakerService _speakerService;

    @Autowired
    public SpeakerController(SpeakerService _speakerService) {
        this._speakerService = _speakerService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> AddSpeaker(@RequestBody CreateSpeakerModel model){
        return _speakerService.AddSpeaker(model);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<List<SpeakerResponse>>> GetAllSpeakers(){
        return _speakerService.GetAllSpeakers();
    }
}
