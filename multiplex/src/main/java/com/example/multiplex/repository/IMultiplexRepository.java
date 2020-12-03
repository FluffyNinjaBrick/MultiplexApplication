package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;

import java.util.List;

public interface IMultiplexRepository {

    // ----------- USER -----------
    public List<User> getAllUsers();
    public User getUserByID(long userID) throws ResourceNotFoundException;
    public User addUser(User user);
    public void deleteUserByID(long userID) throws ResourceNotFoundException;

/*
    // ----------- SCREENING ROOM -----------
    public ScreeningRoom addRoom(ScreeningRoom room);


    // ----------- RESERVATION -----------
    public Reservation addReservation(Reservation reservation);
    public List<Reservation> getReservationsForUser(long userID)


    // ----------- SEAT -----------
    public Seat addSeat(Seat seat);


    // ----------- SCREENING -----------
    public Screening addScreening(Screening screening);
*/
}
