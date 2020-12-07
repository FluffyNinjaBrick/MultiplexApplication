package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.repository.jpaRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("HibernateRepository")
public class MultiplexRepository implements IMultiplexRepository {

    private final UserRepository userRepository;
    private final ScreeningRoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MultiplexRepository(UserRepository userRepository,
                               ScreeningRoomRepository roomRepository,
                               ReservationRepository reservationRepository,
                               SeatRepository seatRepository,
                               ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }



    // ----------- USER -----------
    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserByID(long userID) throws ResourceNotFoundException {
        return this.userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + userID));
    }

    @Override
    public User addUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUserByID(long userID) throws ResourceNotFoundException {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + userID));
        this.userRepository.delete(user);
    }


    // ----------- SCREENING ROOM -----------
    @Override
    public ScreeningRoom getRoomByID(long roomID) throws ResourceNotFoundException {
        return this.roomRepository.findById(roomID)
                .orElseThrow(() -> new ResourceNotFoundException("No screening room exists with ID " + roomID));
    }

    @Override
    public ScreeningRoom addRoom(ScreeningRoom room) {
        return this.roomRepository.save(room);
    }


    // ----------- RESERVATION -----------
    @Override
    public Reservation addReservation(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    @Override
    public Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + userID));
        return user.getReservations();
    }


    // ----------- SEAT -----------
    @Override
    public Seat getSeatByID(long seatID) throws ResourceNotFoundException {
        return this.seatRepository.findById(seatID)
                .orElseThrow(() -> new ResourceNotFoundException("No seat exists with ID " + seatID));
    }

    @Override
    public Seat addSeat(Seat seat) {
        return this.seatRepository.save(seat);
    }


    // ----------- SCREENING -----------
    @Override
    public Screening getScreeningByID(long screeningID) throws ResourceNotFoundException {
        return this.screeningRepository.findById(screeningID)
                .orElseThrow(() -> new ResourceNotFoundException("No screening exists with ID " + screeningID));
    }

    @Override
    public Screening addScreening(Screening screening) {
        return this.screeningRepository.save(screening);
    }


    // ----------- MOVIE -----------
    @Override
    public Movie getMovieByID(long movieID) throws ResourceNotFoundException {
        return this.movieRepository.findById(movieID)
                .orElseThrow(() -> new ResourceNotFoundException("No movie exists with ID " + movieID));
    }

    @Override
    public Movie addMovie(Movie movie) {
        return this.movieRepository.save(movie);
    }


}
