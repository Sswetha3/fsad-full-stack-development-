package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;   // ✅ ADD THIS LINE
import com.example.demo.repository.EventRepository;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAvailableEvents(User user) {

        if (user == null) {
            return List.of();
        }

        String userDomain = user.getEmail().split("@")[1];

        return eventRepository.findAll()
                .stream()
                .filter(event ->
                        event.getAllowedDomain().equals("ALL") ||
                        event.getAllowedDomain().equalsIgnoreCase(userDomain)
                )
                .toList();
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}