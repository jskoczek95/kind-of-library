package com.skoczek.demo.controller;

import com.skoczek.demo.model.Book;
import com.skoczek.demo.model.User;
import com.skoczek.demo.service.BookService;

import com.skoczek.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/user")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/show-books")
    public String listBooks(Model model, @PathVariable("id") Long id){

        model.addAttribute("book", bookService.getBookByOwner(id));
        return "books/list-books";
    }


    @PostMapping("/new-book")
    public String saveBook(@ModelAttribute Book book) {

        bookService.saveBook(book);
        return "redirect:/user/list";
    }

    @GetMapping("/{id}/new-book")
    public String showAddBookForm(Model model, @PathVariable Long id){

        User user = userService.findById(id);
        Book book = new Book();
        book.setUser(user);
        model.addAttribute("book", book);
        return "books/book-form";
    }

    @GetMapping("/{id}/search")
    public String searchByTitle(@RequestParam("searchedTitle") String theTitle, Model model){

        List<Book> books = bookService.searchBookByTitle(theTitle);
        model.addAttribute("book", books);
        return "books/list-books";
    }


}
