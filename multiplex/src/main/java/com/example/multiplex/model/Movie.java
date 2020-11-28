package com.example.multiplex.model;

import javax.persistence.*;

@Entity
@Table(name = "Movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Author")
    private String author;

    @Column(name = "Description")
    private String description;

    public Movie() { super(); }

    public Movie(String title, String author, String description){
        super();
        this.title = title;
        this.author = author;
        this.description = description;
    }
    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
