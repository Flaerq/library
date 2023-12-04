package com.vitaliy.library.dao;


import com.vitaliy.library.model.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private JdbcTemplate template;

    public BookDAO(JdbcTemplate template){
        this.template = template;
    }

    public List<Book> readAll(){
        return template.query("SELECT * FROM book",new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> readById(int id){
        return template.query("SELECT * FROM book WHERE id=?", new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny();
    }

    public void save(Book book){
        template.update("INSERT INTO book(title,author,release_date) VALUES (?,?,?)",book.getTitle(),book.getAuthor(), Date.valueOf(book.getReleaseDate()));
    }

    public void update(int id, Book book){
        template.update("UPDATE book SET title=?, author=?,release_date=? WHERE id=?",book.getTitle(),book.getAuthor(), Date.valueOf(book.getReleaseDate()),id);

    }

    public void delete(int id){
        template.update("DELETE FROM book WHERE id =?",id);
    }
}
