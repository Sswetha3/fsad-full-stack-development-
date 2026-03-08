package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER PAGE =================
   
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/default")
    public String redirectAfterLogin(Authentication auth) {

        User user = userRepo.findByEmail(auth.getName()).orElseThrow();

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/student/dashboard";
        }
    }


    // ================= SAVE USER =================
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role,
            Model model) {

        // 🔴 DUPLICATE CHECK
        if (userRepo.findByEmail(email).isPresent()) {
            model.addAttribute("error", "User already registered");
            return "register";
        }

        // ✅ SAVE USER
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepo.save(user);

        return "redirect:/login";
        
    }
}
