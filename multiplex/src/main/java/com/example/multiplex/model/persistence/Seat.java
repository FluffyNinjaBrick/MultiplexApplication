package com.example.multiplex.model.persistence;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Seats")
public class Seat {

    // ----------- db fields -----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "row_number")
    private Integer rowNumber;


    // ----------- one to many -----------
    @OneToMany(mappedBy = "seat")
    private Set<Reservation> reservations;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_room_id")
    private ScreeningRoom screeningRoom;

    public Seat() { super(); }

    public Seat(Integer seatNumber, Integer rowNumber, ScreeningRoom screeningRoom) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.screeningRoom = screeningRoom;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }
}
