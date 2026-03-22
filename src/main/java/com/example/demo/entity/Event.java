package com.example.demo.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime eventDateTime;
    @Column(length=1000)
    private String instructions;
    private String eventName;
    private int maxTickets;
    private int bookedTickets;

    // IMPORTANT FIELD
    private String allowedDomain;   // "ALL" or "klh.edu.in"
       public Long getId() {
        return id;
    }
       public String getStatus() {
    	    if (eventDateTime == null) return "UNKNOWN";

    	    return eventDateTime.isBefore(LocalDateTime.now())
    	            ? "COMPLETED"
    	            : "UPCOMING";
    	}
    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getMaxTickets() {
        return maxTickets;
    }

    public void setMaxTickets(int maxTickets) {
        this.maxTickets = maxTickets;
    }

    public int getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(int bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public String getAllowedDomain() {
        return allowedDomain;
    }

    public void setAllowedDomain(String allowedDomain) {
        this.allowedDomain = allowedDomain;
    }
    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}