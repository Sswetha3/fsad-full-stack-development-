package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;

import java.util.List;
@Controller
@RequestMapping("/admin")
public class EventController {

    @Autowired
    private EventService eventService;

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin/dashboard";
    }

    // CREATE PAGE
    @GetMapping("/create-event")
    public String createPage(Model model) {
        model.addAttribute("event", new Event());
        return "admin/create-event";
    }

    // SAVE EVENT
    @PostMapping("/create-event")
    public String saveEvent(Event event) {
        event.setBookedTickets(0);
        eventService.createEvent(event);
        return "redirect:/admin/dashboard";
    }

    // DELETE EVENT
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }

    // EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "admin/edit-event";
    }
    // UPDATE EVENT
    @PostMapping("/update-event")
    public String updateEvent(Event event) {

        Event existing = eventService.getEventById(event.getId());

        // preserve booked tickets
        event.setBookedTickets(existing.getBookedTickets());

        eventService.createEvent(event);

        return "redirect:/admin/dashboard";
    }
}