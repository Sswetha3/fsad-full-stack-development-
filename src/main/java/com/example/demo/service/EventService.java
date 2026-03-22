package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import java.util.List;

public interface EventService {

    Event createEvent(Event event);

    List<Event> getAllEvents();

    List<Event> getAvailableEvents(User user);  
    void deleteEvent(Long id);// ✅ ADD THIS
    Event getEventById(Long id);
}