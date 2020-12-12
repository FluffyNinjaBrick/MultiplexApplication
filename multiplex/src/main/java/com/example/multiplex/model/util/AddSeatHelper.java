package com.example.multiplex.model.util;

public class AddSeatHelper {
    private Integer number;
    private Integer row;
    private String roomNumber;

    public AddSeatHelper() { super(); }

    public AddSeatHelper(Integer number, Integer row, String roomNumber) {
        this.number = number;
        this.row = row;
        this.roomNumber = roomNumber;
    }


    // ------------- GETTERS AND SETTERS ------------- //
    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public Integer getRow() { return row; }
    public void setRow(Integer row) { this.row = row; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
}
