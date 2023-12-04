package com.vitaliy.library.controller;

import com.vitaliy.library.dao.PersonDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "people/people";
    }
}
