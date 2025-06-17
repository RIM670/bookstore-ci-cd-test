package com.example.bookstore.services;

import com.example.bookstore.models.User;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Retrieve user from the userService by email
        Optional<User> userOpt = userService.findByEmail(email);

        // If user is not found, throw exception
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Get user from Optional
        User user = userOpt.get();

        // Return a Spring Security User object with user details
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // Password is already encoded
                .roles("USER") // Assigning roles
                .build();
    }
}

