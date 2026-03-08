package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.*;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByUserAndEvent(User user, Event event);

    List<Ticket> findByUser(User user);
}
