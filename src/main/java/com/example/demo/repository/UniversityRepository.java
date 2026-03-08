package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByName(String name);
}
