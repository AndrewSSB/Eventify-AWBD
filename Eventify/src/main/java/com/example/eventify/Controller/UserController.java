package com.example.eventify.Controller;

import com.example.eventify.DTO.User.UserResponse;
import com.example.eventify.DTO.Venue.CreateVenueModel;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService _userService;

    @Autowired
    public UserController(UserService userService) {
        _userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<UserResponse>> GetUserProfile(){
        return _userService.GetUserProfile();
    }
}
