package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableStringValue;

import java.util.Set;

public class Seat {

    // ----------- db fields -----------

    private long id;
    private SimpleIntegerProperty seatNumber;

    private SimpleIntegerProperty rowNumber;


    // ----------- one to many -----------

    private Set<Reservation> reservations;


    private ScreeningRoom screeningRoom;
    private long screeningRoomId;

    public Seat() { super();
        this.screeningRoom = new ScreeningRoom();
        this.seatNumber = new SimpleIntegerProperty();
        this.rowNumber = new SimpleIntegerProperty();}

    public Seat(Integer seatNumber, Integer rowNumber, ScreeningRoom screeningRoom) {
        this();
        this.seatNumber = new SimpleIntegerProperty(seatNumber);
        this.rowNumber = new SimpleIntegerProperty(rowNumber);
        this.screeningRoom = screeningRoom;
    }
    public static final Seat newSeat(){ return new Seat(); }

    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public Integer getSeatNumber() { return seatNumber.getValue(); }
    public ObservableStringValue getSeatNumberObs() { return seatNumber.asString(); }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber.set(seatNumber); }

    public Integer getRowNumber() { return rowNumber.getValue(); }
    public ObservableStringValue getSeatRowObs() { return rowNumber.asString(); }
    public void setRowNumber(Integer rowNumber) { this.rowNumber.set(rowNumber); }

    public ScreeningRoom getScreeningRoom() { return screeningRoom; }
    public void setScreeningRoom(ScreeningRoom screeningRoom) { this.screeningRoom = screeningRoom; }

    public long getScreeningRoomId() {
        return screeningRoomId;
    }

    public void setScreeningRoomId(long screeningRoomId) {
        this.screeningRoomId = screeningRoomId;
    }
}
