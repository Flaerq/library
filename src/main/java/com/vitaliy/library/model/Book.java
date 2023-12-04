package com.vitaliy.library.model;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class Book {

    private int id;

    @NotEmpty(message="Book title should not be empty")
    private String title;

    private String author;

    private LocalDate releaseDate;

    public Book() {
    }

    public Book(String title, String author, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString(){
        return title + "\n" + author + "\n" + releaseDate;
    }
}
