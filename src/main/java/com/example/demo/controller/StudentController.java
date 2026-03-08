package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final TicketRepository ticketRepo;

    // ✅ CONSTRUCTOR
    public StudentController(UserRepository userRepo,
                             EventRepository eventRepo,
                             TicketRepository ticketRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.ticketRepo = ticketRepo;
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("events", eventRepo.findAll());
        return "student/dashboard";
    }

    // ================= CAN APPLY =================
    @GetMapping("/can-apply")
    public String canApply(Model model) {
        model.addAttribute("events", eventRepo.findAll());
        return "student/can-apply";
    }

    // ================= APPLY EVENT =================
    @PostMapping("/apply/{id}")
    public String applyEvent(@PathVariable Long id, Authentication auth) {

        User user = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        boolean alreadyApplied = ticketRepo.existsByUserAndEvent(user, event);

        if (!alreadyApplied && event.getRemainingTickets() > 0) {

            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setEvent(event);

            ticketRepo.save(ticket);

            event.setRemainingTickets(event.getRemainingTickets() - 1);
            eventRepo.save(event);
        }

        return "redirect:/student/applied-events";
    }

    // ================= APPLIED EVENTS =================
    @GetMapping("/applied-events")
    public String appliedEvents(Model model, Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Ticket> tickets = ticketRepo.findByUser(user);

        model.addAttribute("tickets", tickets);
        return "student/applied-events";
    }
}
