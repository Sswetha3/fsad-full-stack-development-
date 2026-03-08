package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    public Ticket() {}

    public Ticket(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Event getEvent() { return event; }

    public void setUser(User user) { this.user = user; }
    public void setEvent(Event event) { this.event = event; }
}
