package com.example.demo.service;

import com.example.demo.entity.Ticket;
import java.util.List;

public interface TicketService {

    String bookTicket(Long userId, Long eventId);   // ✅ ADD

    List<Ticket> getUserTickets(Long userId);
    String cancelTicket(Long userId, Long eventId);// ✅ ADD
}