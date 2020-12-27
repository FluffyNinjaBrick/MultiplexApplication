package com.example.multiplex.model.util;

import java.util.Date;

public class AddScreeningHelper {
    private Integer ticketCost;
    private Date date;
    private String movieTitle;
    private String roomNumber;

    public AddScreeningHelper() { super(); }

    public AddScreeningHelper(Integer ticketCost, Date date, String movieTitle, String roomNumber) {
        this.ticketCost = ticketCost;
        this.date = date;
        this.movieTitle = movieTitle;
        this.roomNumber = roomNumber;
    }


    // ------------- GETTERS AND SETTERS ------------- //

    public Integer getTicketCost() { return ticketCost; }
    public void setTicketCost(Integer ticketCost) { this.ticketCost = ticketCost; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

}
