package com.example.multiplex.model;

import javax.persistence.*;

@Entity
@Table(name = "ScreeningRooms")
public class ScreeningRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String number;

    @Column(name = "floor")
    private Integer floor;


    public ScreeningRoom() { super(); }

    public ScreeningRoom(String number, Integer floor, Integer maximalCapacity){
        super();
        this.number = number;
        this.floor = floor;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
}
