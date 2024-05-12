package com.example.eventify.Seeders;

import com.example.eventify.DTO.Auth.Register.RegisterModel;
import com.example.eventify.Entities.Role;
import com.example.eventify.Entities.User;
import com.example.eventify.Kernel.Constants.Constants;
import com.example.eventify.Repositories.Interfaces.IRoleRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountSeeder {
    private final IUserRepository _userRepository;
    private final IRoleRepository _roleRepository;
    private final PasswordEncoder _passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AccountSeeder.class);

    @Autowired
    public AccountSeeder(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
        _roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles(){
        RegisterModel organizer = new RegisterModel("organizer", "Parola123!", "OrganizerFirst", "OrganizerLast", "organizer@gmail.com", "0741498829");
        RegisterModel admin = new RegisterModel("admin", "Parola123!", "AdminFirst", "AdminLast", "admin@gmail.com", "0741498829");

        Boolean organizerExists = _userRepository.userAlreadyExists(organizer.getEmail(), organizer.getUsername());
        Boolean adminExists = _userRepository.userAlreadyExists(organizer.getEmail(), organizer.getUsername());

        if (organizerExists || adminExists){
            logger.info(String.format(Constants.AC_UserExists, organizer.getUsername() + ", " + admin.getUsername()));
            return;
        }

        Optional<Role> organizerRole = _roleRepository.findByName(Constants.OrganizerRole);
        Optional<Role> adminRole = _roleRepository.findByName(Constants.AdminRole);

        if (organizerRole.isEmpty() || adminRole.isEmpty()){
            logger.info(String.format(Constants.AC_NoRolesFound, Constants.OrganizerRole + ", " + Constants.AdminRole));
            return;
        }

        CreateUserIfNotExists(organizer, organizerRole.get());
        CreateUserIfNotExists(admin, adminRole.get());
    }

    private void CreateUserIfNotExists(RegisterModel model, Role role){
        Boolean alreadyExists = _userRepository.userAlreadyExists(model.getEmail(), model.getUsername());
        if (alreadyExists) {
            return;
        }

        String encodedPassword = _passwordEncoder.encode(model.getPassword());

        User user = new User(model.getUsername(), encodedPassword, model.getFirstName(), model.getLastName(), model.getEmail(), model.getPhoneNumber());

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        _userRepository.save(user);
    }
}
