package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.User;
import com.example.bookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(User user, List<Book> books) {
        double total = books.stream().mapToDouble(Book::getPrice).sum();
        Order order = new Order();
        order.setUser(user);
        order.setBooks(books);
        order.setTotal(total);
        order.setDate(LocalDateTime.now());
        return orderRepository.save(order);
    }
}
