package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.Event.CreateEventModelValidator;
import com.example.eventify.DTO.Event.EventResponse;
import com.example.eventify.DTO.Speaker.CreateSpeakerModel;
import com.example.eventify.DTO.Speaker.CreateSpeakerValidator;
import com.example.eventify.DTO.Speaker.SpeakerResponse;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Speaker;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeakerService {
    private final ISpeakerRepository _speakerRepository;
    private final ModelMapper _mapper;

    @Autowired
    public SpeakerService(ISpeakerRepository _speakerRepository, ModelMapper _mapper) {
        this._speakerRepository = _speakerRepository;
        this._mapper = _mapper;
    }

    public ResponseEntity<ApiResponse<String>> AddSpeaker(CreateSpeakerModel model){
        List<String> validationErrors = CreateSpeakerValidator.Validate(model);
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(validationErrors), HttpStatus.BAD_REQUEST);
        }

        Speaker speaker = _mapper.map(model, Speaker.class);

        _speakerRepository.save(speaker);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<List<SpeakerResponse>>> GetAllSpeakers(){
        List<Speaker> speakers = _speakerRepository.findAll();

        List<SpeakerResponse> response = speakers.stream()
                .map(speaker -> _mapper.map(speaker, SpeakerResponse.class))
                .toList();

        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
}
