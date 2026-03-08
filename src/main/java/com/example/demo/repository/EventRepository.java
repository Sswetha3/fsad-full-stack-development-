package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.*;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByUniversity(University university);
}
