package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    // ✅ BOOK TICKET
    @Override
    public String bookTicket(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElse(null);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (user == null || event == null) {
            return "Invalid Data";
        }

        // DOMAIN CHECK
        String userDomain = user.getEmail().split("@")[1];

        if (!event.getAllowedDomain().equals("ALL") &&
            !event.getAllowedDomain().equalsIgnoreCase(userDomain)) {

            return "Not allowed for your email domain";
        }

        // EVENT FULL
        if (event.getBookedTickets() >= event.getMaxTickets()) {
            return "Event Full";
        }

        // ALREADY BOOKED
        if (ticketRepository.existsByUserAndEvent(user, event)) {
            return "Already booked";
        }

        // DATE CHECK
        if (event.getEventDateTime() == null) {
            return "Event date not set. Contact admin.";
        }

        if (event.getEventDateTime().isBefore(java.time.LocalDateTime.now())) {
            return "Event already started or completed";
        }

        // SAVE
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setEvent(event);

        event.setBookedTickets(event.getBookedTickets() + 1);

        ticketRepository.save(ticket);
        eventRepository.save(event);

        return "Booked Successfully";
    }

    // ✅ GET USER TICKETS
    @Override
    public List<Ticket> getUserTickets(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        return ticketRepository.findByUser(user);
    }

    // ✅ CANCEL TICKET (ONLY ONE METHOD)
    @Override
    public String cancelTicket(Long userId, Long eventId) {

        User user = userRepository.findById(userId).orElse(null);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (user == null || event == null) {
            return "Invalid Data";
        }

        Ticket ticket = ticketRepository.findByUserAndEvent(user, event);

        if (ticket == null) {
            return "No booking found";
        }

        // DELETE
        ticketRepository.delete(ticket);

        // UPDATE COUNT
        event.setBookedTickets(event.getBookedTickets() - 1);
        eventRepository.save(event);

        return "Booking Cancelled";
    }
}