package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.User.UserResponse;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final IUserRepository _userRepository;
    private final ModelMapper _mapper;
    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper mapper){
        _userRepository = userRepository;
        _mapper = mapper;
    }

    public ResponseEntity<ApiResponse<UserResponse>> GetUserProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getPrincipal().toString();

        Optional<User> user = _userRepository.findByUsername(username);

        UserResponse userResponse = _mapper.map(user, UserResponse.class);

        return new ResponseEntity<>(new ApiResponse<>(userResponse), HttpStatus.OK);
    }
}
