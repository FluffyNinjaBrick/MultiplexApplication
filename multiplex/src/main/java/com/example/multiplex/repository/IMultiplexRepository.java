package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;

import java.util.List;
import java.util.Set;

public interface IMultiplexRepository {

    // ----------- USER -----------
    public List<User> getAllUsers();
    public User getUserByID(long userID) throws ResourceNotFoundException;
    public User addUser(User user);
    public void deleteUserByID(long userID) throws ResourceNotFoundException;


    // ----------- SCREENING ROOM -----------
    ScreeningRoom getRoomByID(long roomID) throws ResourceNotFoundException;
    public ScreeningRoom addRoom(ScreeningRoom room);


    // ----------- RESERVATION -----------
    public Reservation addReservation(Reservation reservation);
    public Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException;


    // ----------- SEAT -----------
    Seat getSeatByID(long seatID) throws ResourceNotFoundException;
    public Seat addSeat(Seat seat);


    // ----------- SCREENING -----------
    Screening getScreeningByID(long screeningID) throws ResourceNotFoundException;
    public Screening addScreening(Screening screening);


    // ---------- MOVIE ---------- //
    Movie getMovieByID(long movieID) throws ResourceNotFoundException;
    public Movie addMovie(Movie movie);
}
