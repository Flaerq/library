package com.vitaliy.library.utils;

import com.vitaliy.library.dao.PersonDAO;
import com.vitaliy.library.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonNameValidator implements Validator {

    private PersonDAO personDAO;

    public PersonNameValidator(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;
        Optional<Person> optPerson = personDAO.readAll()
                .stream()
                .filter(i -> i.getName().equals(person.getName()))
                .findAny();
        if(optPerson.isPresent()){
            if (person.getId() == optPerson.get().getId()){
                return;
            }
            errors.rejectValue("name","","Person with this name is already registered");
        }
    }
}
