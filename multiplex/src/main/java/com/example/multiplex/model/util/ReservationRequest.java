package com.example.multiplex.model.util;

public class ReservationRequest {
    private long userId;
    private int seatNumber;
    private int rowNumber;
    private long screeningId;

    public ReservationRequest() { super(); }

    public ReservationRequest(long userId, int seatNumber, int rowNumber, long screeningId) {
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.screeningId = screeningId;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public int getSeatRow() { return seatNumber; }
    public void setSeatRow(int seatRow) { this.seatNumber = seatRow; }

    public int getSeatCol() { return rowNumber; }
    public void setSeatCol(int seatCol) { this.rowNumber = seatCol; }

    public long getScreeningId() { return screeningId; }
    public void setScreeningId(long screeningId) { this.screeningId = screeningId; }
}
