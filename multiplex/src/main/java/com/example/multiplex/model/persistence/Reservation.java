package com.example.multiplex.model.persistence;

import javax.persistence.*;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public Reservation() { super(); }

    public Reservation(User user, Seat seat, Screening screening) {
        super();
        this.user = user;
        this.seat = seat;
        this.screening = screening;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUser() { return user.getId(); }
    public void setUser(User user) { this.user = user; }

    public String getSeat() { return "Row: " + seat.getRowNumber() + ", Number: " + seat.getSeatNumber(); }
    public void setSeat(Seat seat) { this.seat = seat; }

    public long getScreening() { return screening.getId(); }
    public void setScreening(Screening screening) { this.screening = screening; }
}
