package com.vitaliy.library.controller;

import com.vitaliy.library.dao.BookDAO;
import com.vitaliy.library.dao.OwnerRelationDAO;
import com.vitaliy.library.dao.PersonDAO;
import com.vitaliy.library.model.Book;
import com.vitaliy.library.model.Person;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/library/books")
public class BookController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;
    private OwnerRelationDAO ownerRelationDAO;

    public BookController(BookDAO bookDAO, PersonDAO personDAO, OwnerRelationDAO ownerRelationDAO){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.ownerRelationDAO = ownerRelationDAO;
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
                              BindingResult bindingResult,
                              @ModelAttribute("person") Person person,
                              @RequestParam(name="formType") String type,
                              @RequestParam(name="bookId") int id){
        if (type.equals("new")) {
            if (bindingResult.hasErrors()) {
                return "library/books/new";
            }
            bookDAO.save(book);
            return "redirect:/library/books";
        } else if (type.equals("select")){
            ownerRelationDAO.save(person.getId(),id);
        }

        return "redirect:/library/books";

    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book",bookDAO.readById(id).get());
        model.addAttribute("people",personDAO.readAll());
        model.addAttribute("person", new Person());
        Optional<Book> optBook = ownerRelationDAO.readByBookId(id);
        if(optBook.isPresent()){
            model.addAttribute("personBook",ownerRelationDAO.readPersonByBookId(id).get());
            return "library/books/showBorrowed";
        }
        return "library/books/showFree";
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
