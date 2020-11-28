package com.example.multiplex.model;

import javax.persistence.*;

@Entity
@Table(name = "ScreeningRooms")
public class ScreeningRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Number")
    private String number;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "MaximalCapacity")
    private Integer maximalCapacity;

    public ScreeningRoom() { super(); }

    public ScreeningRoom(String number, Integer floor, Integer maximalCapacity){
        super();
        this.number = number;
        this.floor = floor;
        this.maximalCapacity = maximalCapacity;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public Integer getMaximalCapacity() { return maximalCapacity; }
    public void setMaximalCapacity(Integer maximalCapacity) { this.maximalCapacity = maximalCapacity; }
}
