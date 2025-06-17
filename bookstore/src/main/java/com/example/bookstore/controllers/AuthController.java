package com.example.bookstore.controllers;

import com.example.bookstore.models.User;
import com.example.bookstore.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Affiche le formulaire de connexion
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Thymeleaf template : login.html
    }

    // Affiche le formulaire d'inscription
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";  // Thymeleaf template : signup.html
    }

    // Traite l'inscription
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        // Encode le mot de passe avant sauvegarde
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Enregistre l'utilisateur via UserService
        userService.register(user);

        // Redirige vers la page de connexion après inscription
        return "redirect:/login";
    }
}
