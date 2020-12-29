package model;

import java.util.Set;



public class Movie {
    private long id;

    private String title;

    private String author;

    private String description;

    private Set<Screening> screenings;

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
