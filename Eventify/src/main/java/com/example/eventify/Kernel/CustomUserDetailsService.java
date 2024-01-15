package com.example.eventify.Kernel;

import com.example.eventify.Entities.Role;
import com.example.eventify.Entities.User;
import com.example.eventify.Repositories.Interfaces.IRoleRepository;
import com.example.eventify.Repositories.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(input);

        if (user.isEmpty()){
            user = userRepository.findByEmail(input);
        }

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username or email: " + input);
        }

        List<String> authorities = new ArrayList<>();
        for (Role role : user.get().getRoles()) {
            String name = role.getName();
            authorities.add(name);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail() == null ? user.get().getUsername() : user.get().getEmail())
                .password(user.get().getPassword())
                .roles(String.valueOf(authorities))
                .build();
    }
}
