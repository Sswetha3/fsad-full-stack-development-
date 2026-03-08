package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    public AdminController(EventRepository eventRepo, UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
    }

    // ================= ADMIN DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {

        User admin = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        List<Event> events = eventRepo.findByUniversity(admin.getUniversity());
        model.addAttribute("events", events);

        return "admin/dashboard";
    }

    // ================= CREATE EVENT FORM =================
    @GetMapping("/create-event")
    public String createEventForm() {
        return "admin/create-event";
    }

    // ================= SAVE EVENT =================
    @PostMapping("/create-event")
    public String saveEvent(
            @RequestParam String title,
            @RequestParam int totalTickets,
            @RequestParam String eventType,
            @RequestParam String emailRestriction,
            @RequestParam String eventDateTime,
            Authentication auth
    ) {

        User admin = userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Event event = new Event();
        event.setTitle(title);
        event.setTotalTickets(totalTickets);
        event.setRemainingTickets(totalTickets);
        event.setEventType(eventType);
        event.setEmailRestriction(emailRestriction);
        event.setEventDateTime(LocalDateTime.parse(eventDateTime));
        event.setUniversity(admin.getUniversity());
        event.setStatus("ACTIVE");
        event.setLocked(false);

        eventRepo.save(event);

        return "redirect:/admin/dashboard";
    }

    // ================= CANCEL EVENT =================
    @PostMapping("/cancel/{id}")
    public String cancelEvent(@PathVariable Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        event.setStatus("CANCELLED");
        eventRepo.save(event);
        return "redirect:/admin/dashboard";
    }

    // ================= POSTPONE EVENT =================
    @PostMapping("/postpone/{id}")
    public String postponeEvent(@PathVariable Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        event.setStatus("POSTPONED");
        eventRepo.save(event);
        return "redirect:/admin/dashboard";
    }

    // ================= PREPONE EVENT =================
    @PostMapping("/prepone/{id}")
    public String preponeEvent(@PathVariable Long id) {
        Event event = eventRepo.findById(id).orElseThrow();
        event.setStatus("PREPONED");
        eventRepo.save(event);
        return "redirect:/admin/dashboard";
    }
}
