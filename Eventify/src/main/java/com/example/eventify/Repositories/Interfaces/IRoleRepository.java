package com.example.eventify.Repositories.Interfaces;

import com.example.eventify.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.Name = :name")
    Optional<Role> findByName(@Param("name") String name);
}
