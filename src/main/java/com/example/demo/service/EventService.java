package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepo;

    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event getEvent(Long id) {
        return eventRepo.findById(id).orElseThrow();
    }

    public Event save(Event event) {
        return eventRepo.save(event);
    }
}
