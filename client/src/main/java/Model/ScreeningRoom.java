package Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;
public class ScreeningRoom {
    private long id;

    private String number;

    private Integer floor;

    private Set<Seat> seats;

    private Set<Screening> screenings;

    public ScreeningRoom() { super(); }
    public ScreeningRoom(int id) {
        super();
        this.id = id;
    }

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
