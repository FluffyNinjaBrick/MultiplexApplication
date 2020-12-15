package Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Set;

public class Screening {


    private long id;

    private Integer ticketCost;

    private Date date;

    // ----------- one to many -----------
    private Set<Reservation> reservations;


    private Movie movie;


    private ScreeningRoom screeningRoom;

    public Screening() { super(); }
    public Screening(int id) {
        super();
        this.id = id;
    }

    public Screening(Integer ticketCost, Date date, Movie movie, ScreeningRoom screeningRoom) {
        this.ticketCost = ticketCost;
        this.date = date;
        this.movie = movie;
        this.screeningRoom = screeningRoom;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Integer getTicketCost() { return ticketCost; }
    public void setTicketCost(Integer ticketCost) { this.ticketCost = ticketCost; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public long getScreeningRoom() { return screeningRoom.getId(); }
    public void setScreeningRoom(ScreeningRoom screeningRoom) { this.screeningRoom = screeningRoom; }
}
