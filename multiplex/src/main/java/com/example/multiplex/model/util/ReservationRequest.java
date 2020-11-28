package com.example.multiplex.model.util;

public class ReservationRequest {
    private long userId;
    private long seatId;

    public ReservationRequest() { super(); }

    public ReservationRequest(long userId, long seatId) {
        super();
        this.userId = userId;
    }

    // ------------- GETTERS AND SETTERS ------------- //
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getSeatId() { return seatId; }
    public void setSeatId(long seatId) { this.seatId = seatId; }
}
