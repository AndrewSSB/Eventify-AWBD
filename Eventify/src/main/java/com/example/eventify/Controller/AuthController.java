package com.example.eventify.Controller;

import com.example.eventify.DTO.Auth.Login.LoginModel;
import com.example.eventify.DTO.Auth.Register.RegisterModel;
import com.example.eventify.Repositories.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService _authService;

    @Autowired
    public AuthController(AuthService authService){
        _authService = authService;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegisterModel model){
        return _authService.RegisterUser(model);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity Login(@RequestBody LoginModel model){
        return _authService.LoginUser(model);
    }
}
