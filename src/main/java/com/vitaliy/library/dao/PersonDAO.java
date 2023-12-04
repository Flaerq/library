package com.vitaliy.library.dao;

import com.vitaliy.library.model.Person;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {

    private JdbcTemplate template;

    public PersonDAO(JdbcTemplate template){
        this.template = template;
    }

    public List<Person> readAll(){
        return template.query("SELECT * FROM person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> readById(int id){
        return template.query("SELECT * FROM person WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny();
    }

    public void save(Person person){
        template.update("INSERT INTO person(name, date_of_birth) VALUES (?,?)",person.getName(), Date.valueOf(person.getDateOfBirth()));
    }

    public void update(int id, Person person){
        template.update("UPDATE person SET name=?, date_of_birth=? WHERE id=?", person.getName(), Date.valueOf(person.getDateOfBirth()), id);
    }

    public void delete(int id){
        template.update("DELETE FROM person WHERE id=?",id);

    }

}
