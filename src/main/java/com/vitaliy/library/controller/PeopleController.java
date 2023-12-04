package com.vitaliy.library.controller;

import com.vitaliy.library.dao.PersonDAO;
import com.vitaliy.library.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library/people")
public class PeopleController {

    private PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO){
        this.personDAO = personDAO;
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
    public String postNewPerson(@ModelAttribute("person") Person person){
        personDAO.save(person);
        return "redirect:/library/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("person",personDAO.readById(id).orElse(null));
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
                            @ModelAttribute("person") Person person){
        personDAO.update(id,person);
        return "redirect:/library/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/library/people";
    }
}
