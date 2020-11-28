package com.example.multiplex.model.util;

public class ReservationRequest {
    private long userId;
    private long seatId;
    private long screeningId;

    public ReservationRequest() { super(); }

    public ReservationRequest(long userId, long seatId, long screeningId) {
        super();
        this.userId = userId;
        this.seatId = seatId;
        this.screeningId = screeningId;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getSeatId() { return seatId; }
    public void setSeatId(long seatId) { this.seatId = seatId; }

    public long getScreeningId() { return screeningId; }
    public void setScreeningId(long screeningId) { this.screeningId = screeningId; }
}
