package com.example.eventify.Repositories.Interfaces;

import com.example.eventify.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends IBaseRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.Id = :userId")
    Optional<User> findById(@Param("userId") Long userId);
    @Query("SELECT u FROM User u WHERE u.Username = :username")
    Optional<User> findByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u WHERE u.Email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.Email = :email OR u.Username = :username")
    Boolean userAlreadyExists(@Param("email") String email, @Param("username") String username);
}
