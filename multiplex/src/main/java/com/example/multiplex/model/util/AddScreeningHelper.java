package com.example.multiplex.model.util;

import java.util.Date;

public class AddScreeningHelper {
    private Integer ticketCost;
    private Date date;
    private long movieID;
    private long roomID;

    public AddScreeningHelper() { super(); }

    public AddScreeningHelper(Integer ticketCost, Date date, long movieID, long roomID) {
        this.ticketCost = ticketCost;
        this.date = date;
        this.movieID = movieID;
        this.roomID = roomID;
    }


    // ------------- GETTERS AND SETTERS ------------- //

    public Integer getTicketCost() { return ticketCost; }
    public void setTicketCost(Integer ticketCost) { this.ticketCost = ticketCost; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public long getMovieID() { return movieID; }
    public void setMovieID(long movieID) { this.movieID = movieID; }

    public long getRoomID() { return roomID; }
    public void setRoomID(long roomID) { this.roomID = roomID; }

}
