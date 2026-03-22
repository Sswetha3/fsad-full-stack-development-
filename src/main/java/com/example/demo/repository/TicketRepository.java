package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.entity.Event;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUser(User user);

    // ✅ ADD THIS METHOD
    boolean existsByUserAndEvent(User user, Event event);
    Ticket findByUserAndEvent(User user, Event event);
}