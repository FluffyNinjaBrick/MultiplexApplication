package model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;

import java.util.Set;



public class Movie {
    private long id;

    private SimpleStringProperty title;

    private SimpleStringProperty author;

    private SimpleStringProperty description;

    private Set<Screening> screenings;

    public Movie() { super();
        this.title = new SimpleStringProperty("");
        this.author = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
    }
    public static final Movie newMovie(){ return new Movie(); }
    public Movie(String title, String author, String description){
        this();
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.description = new SimpleStringProperty(description);
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title.getValue(); }
    public ObservableStringValue getTitleObs() { return title; }
    public void setTitle(String title) { this.title.set(title); }

    public String getAuthor() { return author.getValue(); }
    public ObservableStringValue getAuthorObs() { return author; }
    public void setAuthor(String author) { this.author.set(author); }

    public String getDescription() { return description.getValue(); }
    public ObservableStringValue getDescriptionObs() { return description; }
    public void setDescription(String description) { this.description.set(description); }
}
