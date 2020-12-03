package com.example.multiplex.model.util;

public class AddSeatHelper {
    private Integer number;
    private Integer row;
    private long roomID;

    public AddSeatHelper() { super(); }

    public AddSeatHelper(Integer number, Integer row, long roomID) {
        this.number = number;
        this.row = row;
        this.roomID = roomID;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Integer getRow() { return row; }
    public void setRow(Integer row) { this.row = row; }

    public long getRoomID() { return roomID; }
    public void setRoomID(long roomID) { this.roomID = roomID; }
}
