package com.example.eventify.Repositories.Services;

import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Kernel.GenericResponse.ApiResponse.ApiResponse;
import com.example.eventify.DTO.Auth.Login.LoginModel;
import com.example.eventify.DTO.Auth.Login.LoginResponse;
import com.example.eventify.DTO.Auth.Register.RegisterModel;
import com.example.eventify.DTO.Auth.Register.RegisterModelValidator;
import com.example.eventify.DTO.Auth.Register.RegisterResponse;
import com.example.eventify.Entities.Role;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.JwtUtils;
import com.example.eventify.Repositories.Interfaces.IRoleRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import com.example.eventify.Seeders.AccountSeeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final IUserRepository _userRepository;
    private final IRoleRepository _roleRepository;
    private final PasswordEncoder _passwordEncoder;
    private final AuthenticationManager _authManager;
    private final JwtUtils _jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager, IRoleRepository roleRepository, JwtUtils jwtUtils){
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
        _authManager = authManager;
        _roleRepository = roleRepository;
        _jwtUtils = jwtUtils;
    }

    public ResponseEntity<ApiResponse<RegisterResponse>> RegisterUser(RegisterModel model){
        List<String> validationErrors = RegisterModelValidator.Validate(model);
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse<>(validationErrors), HttpStatus.BAD_REQUEST);
        }

        Boolean alreadyExists = _userRepository.userAlreadyExists(model.getEmail(), model.getUsername());
        if (alreadyExists) {
            logger.info(Constants.AU_AlreadyRegistered);
            return new ResponseEntity<>(new ApiResponse<>(Constants.GenericMessage), HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = _passwordEncoder.encode(model.getPassword());

        User user = new User(model.getUsername(), encodedPassword, model.getFirstName(), model.getLastName(), model.getEmail(), model.getPhoneNumber());
        Optional<Role> role = _roleRepository.findByName("User");

        if (role.isEmpty()){
            logger.info(Constants.AU_NoRoles);
            return new ResponseEntity<>(new ApiResponse<>(Constants.ContactTeam), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role.get());
        user.setRoles(roles);

        _userRepository.save(user);

        String accessToken = _jwtUtils.GenerateToken(user);
        return new ResponseEntity<>(new ApiResponse<>(new RegisterResponse(accessToken)), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<LoginResponse>> LoginUser(LoginModel model){
        Optional<User> user = _userRepository.findByEmail(model.getEmail());
        if (user.isEmpty()){
            user = _userRepository.findByUsername(model.getUsername());
        }

        if (user.isEmpty()){
            logger.info(Constants.InvalidUsernameOrPassword);
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidUsernameOrPassword), HttpStatus.BAD_REQUEST);
        }

        boolean matches = _passwordEncoder.matches(model.getPassword(), user.get().getPassword());
        if (!matches){
            logger.info(Constants.InvalidUsernameOrPassword);
            return new ResponseEntity<>(new ApiResponse<>(Constants.InvalidUsernameOrPassword), HttpStatus.BAD_REQUEST);
        }

        _authManager.authenticate(new UsernamePasswordAuthenticationToken
            (
                model.getUsername() == null ? model.getEmail() : model.getUsername(),
                model.getPassword()
            )
        );

        String accessToken = _jwtUtils.GenerateToken(user.get());
        return new ResponseEntity<>(new ApiResponse<>(new LoginResponse(accessToken)), HttpStatus.OK);
    }
}
