package com.vitaliy.library.controller;

import com.vitaliy.library.dao.PersonDAO;
import com.vitaliy.library.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
