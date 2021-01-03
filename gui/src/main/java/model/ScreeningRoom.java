package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;
public class ScreeningRoom {
    private long id;

    private SimpleStringProperty number;

    private SimpleIntegerProperty floor;
    private SimpleIntegerProperty capacity;

    private Set<Seat> seats;

    private Set<Screening> screenings;

    public ScreeningRoom() {
        super();
        number = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        capacity = new SimpleIntegerProperty();

    }
    public ScreeningRoom(int id) {
        this();
        this.id = id;
    }

    public ScreeningRoom(String number, Integer floor, Integer maximalCapacity){
        super();
        this.number.set(number);
        this.floor.set(floor);
    }
    public static final ScreeningRoom newScreeningRoom(){ return new ScreeningRoom(); }
    // ------------- GETTERS AND SETTERS ------------- //
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNumber() { return number.getValue(); }
    public void setNumber(String number) { this.number.set(number); }

    public Integer getFloor() { return floor.getValue(); }
    public void setFloor(Integer floor) { this.floor.set(floor); }

    public Integer getCapacity() {
        return capacity.getValue();
    }

    public void setCapacity(Integer capacity) {
        this.capacity.set(capacity);
    }
}
