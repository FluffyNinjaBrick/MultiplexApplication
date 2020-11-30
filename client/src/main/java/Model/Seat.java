package Model;

import java.util.Set;

public class Seat {

    private long id;

    private Integer seatNumber;

    private Integer rowNumber;

    private Set<Reservation> reservations;

    private ScreeningRoom screeningRoom;

    public Seat() { super(); }

    public Seat(Integer seatNumber, Integer rowNumber) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatNumber(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }
}
