package com.example.bookstore.controllers;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.CartItem;
import com.example.bookstore.repositories.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Display all books
    @GetMapping("/books")
    public String showBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    // Add book to cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String title, HttpSession session) {
        if (title == null) return "redirect:/books";

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        Optional<Book> bookOpt = bookRepository.findByTitle(title);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            boolean found = false;

            for (CartItem item : cart) {
                if (item.getBook().getTitle().equals(book.getTitle())) {
                    item.incrementQuantity();
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(book));
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/books";
    }

    // View cart page
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        double total = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    // Delete a book from cart
    @PostMapping("/cart/delete")
    public String removeFromCart(@RequestParam Long bookId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getBook().getId().equals(bookId));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    // Place order (clear cart)
    @PostMapping("/order")
    public String placeOrder(HttpSession session) {
        session.removeAttribute("cart");
        return "order";
    }
}
