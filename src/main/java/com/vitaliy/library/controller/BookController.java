package com.vitaliy.library.controller;

import com.vitaliy.library.dao.BookDAO;
import com.vitaliy.library.model.Book;
import jakarta.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library/books")
public class BookController {

    private BookDAO bookDAO;

    public BookController(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String books(Model model){
        model.addAttribute("books", bookDAO.readAll());
        return "library/books/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){

        return "library/books/new";
    }

    @PostMapping()
    public String newPostBook(@ModelAttribute("book") @Valid Book book,
                              BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "library/books/new";
        }
        bookDAO.save(book);
        return "redirect:/library/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book",bookDAO.readById(id).get());
        return "library/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book",bookDAO.readById(id).get());
        return "library/books/edit";
    }

    @PatchMapping("/{id}")
    public String patchEdit(@PathVariable("id") int id,
                            @ModelAttribute("book") @Valid Book book,
                            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "library/books/edit";
        }
        bookDAO.update(id,book);
        return "redirect:/library/books/{id}";
    }


}
