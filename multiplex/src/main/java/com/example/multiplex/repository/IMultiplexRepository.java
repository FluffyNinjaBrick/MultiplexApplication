package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.AddScreeningHelper;
import com.example.multiplex.model.util.AddSeatHelper;
import com.example.multiplex.model.util.AddUserHelper;
import com.example.multiplex.model.util.ReservationRequest;

import java.util.List;
import java.util.Set;

public interface IMultiplexRepository {

    // ----------- USER -----------
    List<User> getAllUsers();
    User getUserByID(long userID) throws ResourceNotFoundException;
    User getUserByUsername(String username) throws ResourceNotFoundException;
    User addUser(AddUserHelper helper);
    void deleteUserByID(long userID) throws ResourceNotFoundException;


    // ----------- SCREENING ROOM -----------
    ScreeningRoom getRoomByID(long roomID) throws ResourceNotFoundException;
    ScreeningRoom addRoom(ScreeningRoom room);


    // ----------- RESERVATION -----------
    Reservation addReservation(Reservation reservation);
    Reservation addReservation(ReservationRequest request) throws ResourceNotFoundException;
    Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException;
    Integer calculateReservation(long screening_id, long user_id);
    Integer calculateAllReservations(long user_id) throws ResourceNotFoundException;


    // ----------- SEAT -----------
    Seat getSeatByID(long seatID) throws ResourceNotFoundException;
    Seat addSeat(Seat seat);
    Seat addSeat(AddSeatHelper helper) throws ResourceNotFoundException;
    List<Seat> showEmptySeatsForScreening(long screening_id);


    // ----------- SCREENING -----------
    Screening getScreeningByID(long screeningID) throws ResourceNotFoundException;
    Screening addScreening(Screening screening);
    Screening addScreening(AddScreeningHelper helper) throws ResourceNotFoundException;
    List<Screening> getScreeningsOnOffer();


    // ---------- MOVIE ---------- //
    Movie getMovieByID(long movieID) throws ResourceNotFoundException;
    Movie addMovie(Movie movie);
    List<Movie> getMoviesOnOffer();

}
