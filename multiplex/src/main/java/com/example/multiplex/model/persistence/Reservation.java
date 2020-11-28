package com.example.multiplex.model.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;

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


    public Reservation() { super(); }

    public Reservation(User user, Seat seat) {
        super();
        this.user = user;
        this.seat = seat;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUser() { return user.getId(); }
    public void setUser(User user) { this.user = user; }

    public String getSeat() { return "Row: " + seat.getRowNumber() + ", Number: " + seat.getSeatNumber(); }
    public void setSeat(Seat seat) { this.seat = seat; }

}
