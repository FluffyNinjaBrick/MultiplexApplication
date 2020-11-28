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


    public Reservation() { super(); }

    public Reservation(User user, Seat seat) {
        super();
        this.user = user;
        this.seat = seat;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

}
