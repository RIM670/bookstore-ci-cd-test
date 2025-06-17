package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book("Book 1", "Tasnim", 19.99));
        books.add(new Book("Book 2", "tass", 19.99));
        // Ajoute plusieurs livres ici
    }

    public List<Book> getAllBooks() {
        return books;
    }
}

