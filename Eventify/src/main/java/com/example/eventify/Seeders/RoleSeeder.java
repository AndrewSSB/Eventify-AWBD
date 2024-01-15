package com.example.eventify.Seeders;

import com.example.eventify.Entities.Role;
import com.example.eventify.Repositories.Interfaces.IRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleSeeder {
    @Autowired
    private IRoleRepository roleRepository;

    @PostConstruct
    public void seedRoles(){
        CreateRoleIfNotExists("User");
        CreateRoleIfNotExists("Organizer");
        CreateRoleIfNotExists("Admin");
    }

    private void CreateRoleIfNotExists(String roleName){
        Optional<Role> existingRole = roleRepository.findByName(roleName);

        if (existingRole.isEmpty()){
            Role newRole = new Role(roleName);
            roleRepository.save(newRole);
        }
    }
}
