package com.example.multiplex.model.persistence;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;
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

    /*@ManyToOne(optional = false)
    @JoinColumn(name = "screening_room_id")
    private ScreeningRoom screeningRoom;*/

    //TODO - add movie foreign key

    public Screening() { super(); }

    public Screening(Integer ticketCost) {
        this.ticketCost = ticketCost;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Integer getTicketCost() { return ticketCost; }
    public void setTicketCost(Integer ticketCost) { this.ticketCost = ticketCost; }
}
