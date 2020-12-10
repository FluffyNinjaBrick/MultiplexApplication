package com.example.multiplex.model.util;

public class ReservationRequest {
    private long userId;
    private Integer seatNumber;
    private Integer rowNumber;
    private long screeningId;

    public ReservationRequest() { super(); }

    public ReservationRequest(long userId, Integer seatNumber, Integer rowNumber, long screeningId) {
        this.userId = userId;
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.screeningId = screeningId;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public Integer getSeatNumber() { return seatNumber; }
    public void setSeatRow(Integer seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setSeatCol(Integer seatNumber) { this.rowNumber = seatNumber; }

    public long getScreeningId() { return screeningId; }
    public void setScreeningId(long screeningId) { this.screeningId = screeningId; }
}
