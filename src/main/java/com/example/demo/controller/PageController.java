package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.example.demo.entity.User;
import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;
import com.example.demo.service.EventService;
import com.example.demo.service.TicketService;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;   // 🔥 ADD THIS

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // REGISTER PAGE
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/student/applied")
    public String appliedEvents(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        model.addAttribute("tickets", ticketService.getUserTickets(user.getId()));

        return "student/applied-events";
    }
    // STUDENT DASHBOARD
    @GetMapping("/student/dashboard")
    public String studentDashboard(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        // 🔥 GET EVENTS
        List<Event> events = eventService.getAvailableEvents(user);

        // 🔥 GET USER TICKETS
        List<Ticket> tickets = ticketService.getUserTickets(user.getId());

        // 🔥 EXTRACT BOOKED EVENT IDs
        List<Long> bookedEventIds = tickets.stream()
                .map(t -> t.getEvent().getId())
                .toList();

        // 🔥 SEND TO HTML
        model.addAttribute("events", events);
        model.addAttribute("bookedEventIds", bookedEventIds);

        return "student/dashboard";
    }
}