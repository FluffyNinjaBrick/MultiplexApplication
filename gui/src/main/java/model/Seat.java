package model;

import java.util.Set;

public class Seat {

    // ----------- db fields -----------

    private long id;
    private Integer seatNumber;

    private Integer rowNumber;


    // ----------- one to many -----------

    private Set<Reservation> reservations;


    private ScreeningRoom screeningRoom;

    public Seat() { super(); }

    public Seat(Integer seatNumber, Integer rowNumber, ScreeningRoom screeningRoom) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.screeningRoom = screeningRoom;
    }
    public static final Seat newSeat(){ return new Seat(); }

    // ------------- GETTERS AND SETTERS ------------- //
    // note: these might occasionally return IDs, not the actual structure.
    //       This is done to avoid infinite recursion in http responses.
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public long getScreeningRoom() { return screeningRoom.getId(); }

    public void setScreeningRoom(ScreeningRoom screeningRoom) { this.screeningRoom = screeningRoom; }
}
