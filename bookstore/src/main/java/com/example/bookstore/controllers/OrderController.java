package com.example.bookstore.controllers;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.CartItem;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.User;
import com.example.bookstore.services.OrderService;
import com.example.bookstore.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/place")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails, HttpSession session, Model model) {
        if (userDetails == null) {
            System.out.println("DEBUG - No authenticated user found");
            return "redirect:/login";
        }

        Optional<User> optionalUser = userService.findByEmail(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            System.out.println("DEBUG - User not found in database");
            return "redirect:/login";
        }

        User user = optionalUser.get();

        // Retrieve cart from session (as List<CartItem>)
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        if (cartItems == null || cartItems.isEmpty()) {
            System.out.println("DEBUG - Redirecting to /cart due to missing or empty cart.");
            return "redirect:/cart";
        }

        // Convert CartItem -> Book
        List<Book> books = cartItems.stream()
                .map(CartItem::getBook)
                .collect(Collectors.toList());

        Order order = orderService.placeOrder(user, books);

        // Clear cart
        session.removeAttribute("cart");
        model.addAttribute("order", order);

        return "order"; // thymeleaf view name
    }
}
