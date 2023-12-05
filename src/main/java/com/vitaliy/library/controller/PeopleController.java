package com.vitaliy.library.controller;

import com.vitaliy.library.dao.OwnerRelationDAO;
import com.vitaliy.library.dao.PersonDAO;
import com.vitaliy.library.model.Book;
import com.vitaliy.library.model.Person;
import com.vitaliy.library.utils.PersonNameValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/library/people")
public class PeopleController {

    private PersonNameValidator personNameValidator;
    private PersonDAO personDAO;
    private OwnerRelationDAO ownerRelationDAO;

    public PeopleController(PersonNameValidator personNameValidator,
                            PersonDAO personDAO,
                            OwnerRelationDAO ownerRelationDAO){
        this.personNameValidator = personNameValidator;
        this.personDAO = personDAO;
        this.ownerRelationDAO = ownerRelationDAO;
    }


    @GetMapping()
    public String peoplePage(Model model){
        model.addAttribute("people",personDAO.readAll());
        return "library/people/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "library/people/new";
    }

    @PostMapping()
    public String postNewPerson(@ModelAttribute("person") @Valid Person person,
                                BindingResult bindingResult){
        personNameValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "library/people/new";
        }
        personDAO.save(person);
        return "redirect:/library/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("person",personDAO.readById(id).orElse(null));
        List<Book> personBooks = ownerRelationDAO.read(id);
        if (!personBooks.isEmpty()){
            model.addAttribute("books",personBooks);
            return "library/people/showWithBooks";
        }

        return "library/people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("person",personDAO.readById(id).get());
        return "library/people/edit";
    }

    @PatchMapping("/{id}")
    public String patchEdit(@PathVariable("id") int id,
                            @ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult){
        personNameValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "library/people/edit";
        }
        personDAO.update(id,person);
        return "redirect:/library/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/library/people";
    }
}
