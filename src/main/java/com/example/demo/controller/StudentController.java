package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.*;
import com.example.demo.service.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    // BOOK TICKET
    @PostMapping("/book")
    public String bookTicket(@RequestParam Long userId,
                             @RequestParam Long eventId,
                             RedirectAttributes ra) {

        String result = ticketService.bookTicket(userId, eventId);

        ra.addFlashAttribute("message", result);
        System.out.println("BOOK CLICKED: user=" + userId + " event=" + eventId);
        return "redirect:/student/dashboard";
    }
    @PostMapping("/unbook")
    public String cancel(@RequestParam Long userId,
                         @RequestParam Long eventId,
                         RedirectAttributes ra) {

        String result = ticketService.cancelTicket(userId, eventId);

        ra.addFlashAttribute("message", result);

        return "redirect:/student/applied";
    }
}