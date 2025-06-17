package com.example.bookstore.services;

import com.example.bookstore.models.User;
import com.example.bookstore.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    // Injection du repository dans le constructeur
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        userRepository.save(user);  // Sauvegarde bien en base de données
        System.out.println("User registered: " + user.getEmail());
        System.out.println("Password (encoded): " + user.getPassword());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
