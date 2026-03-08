package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;

    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public boolean alreadyApplied(User user, Event event) {
        return ticketRepo.existsByUserAndEvent(user, event);
    }

    public void applyTicket(User user, Event event) {
        Ticket ticket = new Ticket(user, event);
        ticketRepo.save(ticket);
    }

    public List<Ticket> getAppliedTickets(User user) {
        return ticketRepo.findByUser(user);
    }
}
