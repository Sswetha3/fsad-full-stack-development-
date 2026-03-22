package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // REGISTER
    @PostMapping("/register")
    public String register(User user) {
        userService.register(user);
        return "redirect:/login";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(User user, HttpSession session) {

        Optional<User> existingUser =
                userService.login(user.getEmail(), user.getPassword());

        if (existingUser.isPresent()) {

            User u = existingUser.get();

            // STORE SESSION
            session.setAttribute("loggedUser", u);

            System.out.println("ROLE = " + u.getRole());

            if (u.getRole() == Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }

        } else {
            return "redirect:/login?error=true";
        }
    }
}