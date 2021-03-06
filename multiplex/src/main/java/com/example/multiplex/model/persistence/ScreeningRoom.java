package com.example.multiplex.model.persistence;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Screening_Rooms")
public class ScreeningRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String number;

    @Column(name = "floor")
    private Integer floor;

    // ----------- one to many -----------
    @OneToMany(mappedBy = "screeningRoom")
    private Set<Seat> seats;

    @OneToMany(mappedBy = "screeningRoom")
    private Set<Screening> screenings;

    public ScreeningRoom() { super(); }

    public ScreeningRoom(String number, Integer floor, Integer maximalCapacity){
        super();
        this.number = number;
        this.floor = floor;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
}
