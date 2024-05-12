package com.example.eventify.Repositories.Interfaces;

import com.example.eventify.Entities.Event;
import com.example.eventify.Entities.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRegistrationRepository extends IBaseRepository<Registration, Long>{
    @Query("SELECT r FROM Registration r  WHERE r.Event.Id = :eventId")
    List<Event> findEvents(@Param("eventId") Long eventId);
}
