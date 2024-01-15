package com.example.eventify.Repositories.Services;

import com.example.eventify.DTO.Auth.Login.LoginModel;
import com.example.eventify.DTO.Auth.Login.LoginResponse;
import com.example.eventify.DTO.Auth.Register.RegisterModel;
import com.example.eventify.DTO.Auth.Register.RegisterResponse;
import com.example.eventify.Entities.Role;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.JwtUtils;
import com.example.eventify.Repositories.Interfaces.IRoleRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authManager, IRoleRepository roleRepository){
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
        _authManager = authManager;
        _roleRepository = roleRepository;
    }

    public ResponseEntity<RegisterResponse> RegisterUser(RegisterModel model){
        if (model.getEmail() == null ||
            model.getUsername() == null ||
            model.getPassword() == null ||
            model.getLastName() == null ||
            model.getFirstName() == null ||
            model.getPhoneNumber() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Maybe specify a message
        }

        Boolean alreadyExists = _userRepository.userAlreadyExists(model.getEmail(), model.getUsername());
        if (alreadyExists){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Maybe specify a message
        }

        String encodedPassword = _passwordEncoder.encode(model.getPassword());

        User user = new User(model.getUsername(), encodedPassword, model.getFirstName(), model.getLastName(), model.getEmail(), model.getPhoneNumber());
        Optional<Role> role = _roleRepository.findByName("User");

        if (role.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Maybe specify a message
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role.get());
        user.setRoles(roles);

        _userRepository.save(user);

        String accessToken = JwtUtils.GenerateToken(user);
        RegisterResponse response = new RegisterResponse(accessToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<LoginResponse> LoginUser(LoginModel model){
        Optional<User> user = _userRepository.findByEmail(model.getEmail());
        if (user.isEmpty()){
            user = _userRepository.findByUsername(model.getUsername());
        }

        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean matches = _passwordEncoder.matches(model.getPassword(), user.get().getPassword());
        if (!matches){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        Authentication authentication = _authManager.authenticate(new UsernamePasswordAuthenticationToken
//            (
//                model.getUsername() == null ? model.getEmail() : model.getUsername(),
//                user.get().getPassword()
//            )
//        );

        String accessToken = JwtUtils.GenerateToken(user.get());

        return new ResponseEntity<>(new LoginResponse(accessToken), HttpStatus.OK);
    }
}
