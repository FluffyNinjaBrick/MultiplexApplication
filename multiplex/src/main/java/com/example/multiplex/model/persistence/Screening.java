package com.example.multiplex.model.persistence;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Screenings")
public class Screening {

    // ----------- db fields -----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ticket_cost")
    private Integer ticketCost;

    @Column(name = "date")
    private Date date;

    // ----------- one to many -----------
    @OneToMany(mappedBy = "screening")
    private Set<Reservation> reservations;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_room_id")
    private ScreeningRoom screeningRoom;

    public Screening() { super(); }

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
