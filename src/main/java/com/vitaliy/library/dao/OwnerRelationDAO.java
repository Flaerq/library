package com.vitaliy.library.dao;

import com.vitaliy.library.dao.mappers.RelationBookRowMapper;
import com.vitaliy.library.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerRelationDAO {

    private JdbcTemplate template;

    public OwnerRelationDAO(JdbcTemplate template) {
        this.template = template;
    }

    public List<Book> read(int personID){
        return template.query("SELECT person_id, book.title,book.author, book.release_date FROM relation JOIN book ON book.id = relation.book_id WHERE person_id=?",
                new Object[]{personID},
                new RelationBookRowMapper());
    }

    public void save(int personID, int bookID){
        template.update("INSERT INTO  relation(person_id,book_id) VALUES (?,?)",personID,bookID);
    }

    public void deleteByBookId(int bookID){
        template.update("DELETE FROM relation WHERE book_id=?",bookID);
    }

    public void deleteByPersonId(int personID){
        template.update("DELETE FROM relation WHERE person_id=?",personID);
    }

}
