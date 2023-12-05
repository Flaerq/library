package com.vitaliy.library.dao.mappers;

import com.vitaliy.library.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RelationBookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setReleaseDate(rs.getDate("release_date").toLocalDate());
        return book;
    }
}
