package com.example.multiplex.model.persistence;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    // ----------- one to many -----------
    @OneToMany(mappedBy = "movie")
    private Set<Screening> screenings;

    public Movie() { super(); }

    public Movie(String title, String author, String description){
        super();
        this.title = title;
        this.author = author;
        this.description = description;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
