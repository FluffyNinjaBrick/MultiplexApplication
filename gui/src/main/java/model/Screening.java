package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;

import java.util.Date;
import java.util.Set;

public class Screening {


    private long id;

    private SimpleIntegerProperty ticketCost;

    private SimpleStringProperty date;

    // ----------- one to many -----------
    private Set<Reservation> reservations;


    private Movie movie;
    private long movieId;

    private ScreeningRoom screeningRoom;
    private SimpleLongProperty screeningRoomId;

    public Screening() { super();
        this.ticketCost = new SimpleIntegerProperty();
        this.date = new SimpleStringProperty();
        this.screeningRoomId = new SimpleLongProperty();
    }
    public Screening(int id) {
        this();
        this.id = id;


    }

    public Screening(Integer ticketCost, String date, Movie movie, ScreeningRoom screeningRoom) {
        this.ticketCost = new SimpleIntegerProperty(ticketCost);
        this.date = new SimpleStringProperty(date);
        this.movie = movie;
        this.screeningRoom = screeningRoom;
    }
    public static final Screening newScreening(){ return new Screening(); }

    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Integer getTicketCost() { return ticketCost.getValue(); }
    public ObservableStringValue getCostObs() { return ticketCost.asString(); }
    public void setTicketCost(Integer ticketCost) { this.ticketCost.set(ticketCost); }

    public String getDate() { return date.getValue(); }
    public ObservableStringValue getDateObs() { return date; }
    public void setDate(String date) { this.date.set(date); }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public long getScreeningRoom() { return screeningRoom.getId(); }
    public void setScreeningRoom(ScreeningRoom screeningRoom) { this.screeningRoom = screeningRoom; }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getScreeningRoomId() {
        return screeningRoomId.getValue();
    }
    public ObservableStringValue getRoomObs() { return screeningRoomId.asString(); }
    public void setScreeningRoomId(long screeningRoomId) {
        this.screeningRoomId.set(screeningRoomId);
    }
}
