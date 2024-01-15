package com.example.eventify.Repositories.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IBaseRepository<T, Id> extends JpaRepository<T, Id> {
    Optional<T> findById(Id id);
}
