package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.Registration.CreateRegistrationModel;
import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Registration;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IEventRepository;
import com.example.eventify.Repositories.Interfaces.IRegistrationRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RegistrationService {
    private final IRegistrationRepository _registrationRepository;
    private final IUserRepository _userRepository;
    private final IEventRepository _eventRepository;

    public RegistrationService(IRegistrationRepository _registrationRepository, IUserRepository _userRepository, IEventRepository _eventRepository) {
        this._registrationRepository = _registrationRepository;
        this._userRepository = _userRepository;
        this._eventRepository = _eventRepository;
    }

    public ResponseEntity<ApiResponse<String>> CreateRegistration(CreateRegistrationModel model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        Optional<User> user = _userRepository.findByUsername(username);

        if (user.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidUser), HttpStatus.BAD_REQUEST);
        }

        Optional<Event> eventOptional = _eventRepository.findById(model.getEventId());
        if (eventOptional.isEmpty()){
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidEvent), HttpStatus.BAD_REQUEST);
        }

        if (IsRegistrationLimitReached(eventOptional.get())){
            return new ResponseEntity<>(new ApiResponse<>(Constants.RegistrationLimit), HttpStatus.BAD_REQUEST);
        }

        Registration registration = new Registration();
        registration.setStatus("PENDING");

        _registrationRepository.save(registration);

        return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.OK);
    }

    private boolean IsRegistrationLimitReached(Event event){
        return event.getVenue().getCapacity() > 0;
    }
}
