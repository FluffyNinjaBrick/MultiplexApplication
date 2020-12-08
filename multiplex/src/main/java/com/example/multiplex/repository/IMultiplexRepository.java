package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.AddScreeningHelper;

import java.util.List;
import java.util.Set;

public interface IMultiplexRepository {

    // ----------- USER -----------
    List<User> getAllUsers();
    User getUserByID(long userID) throws ResourceNotFoundException;
    User addUser(User user);
    void deleteUserByID(long userID) throws ResourceNotFoundException;


    // ----------- SCREENING ROOM -----------
    ScreeningRoom getRoomByID(long roomID) throws ResourceNotFoundException;
    ScreeningRoom addRoom(ScreeningRoom room);


    // ----------- RESERVATION -----------
    Reservation addReservation(Reservation reservation);
    Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException;


    // ----------- SEAT -----------
    Seat getSeatByID(long seatID) throws ResourceNotFoundException;
    Seat addSeat(Seat seat);


    // ----------- SCREENING -----------
    Screening getScreeningByID(long screeningID) throws ResourceNotFoundException;
    Screening addScreening(Screening screening);
    Screening addScreening(AddScreeningHelper helper) throws ResourceNotFoundException;


    // ---------- MOVIE ---------- //
    Movie getMovieByID(long movieID) throws ResourceNotFoundException;
    Movie addMovie(Movie movie);
}
