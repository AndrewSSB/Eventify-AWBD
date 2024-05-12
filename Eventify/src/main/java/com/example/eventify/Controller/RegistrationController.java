package com.example.eventify.Controller;

import com.example.eventify.DTO.Registration.CancelRegistrationModel;
import com.example.eventify.DTO.Registration.CreateRegistrationModel;
import com.example.eventify.DTO.Speaker.CreateSpeakerModel;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> AddRegistration(@RequestBody CreateRegistrationModel model){
        return registrationService.CreateRegistration(model);
    }

    @ResponseBody
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> CancelRegistration(@RequestBody CancelRegistrationModel model){
        return registrationService.CancelRegistration(model);
    }
}
