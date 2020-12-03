package com.example.multiplex.controller;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.AddScreeningHelper;
import com.example.multiplex.model.util.AddSeatHelper;
import com.example.multiplex.model.util.ReservationRequest;
import com.example.multiplex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserRepository userRepository;
    private final ScreeningRoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final MultiplexRepository repository;

    @Autowired
    public RestController(UserRepository userRepository, ScreeningRoomRepository roomRepository, ReservationRepository reservationRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository, MovieRepository movieRepository, MultiplexRepository repository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.repository = repository;
    }


    // ---------- USER ---------- //

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.repository.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable long id) throws ResourceNotFoundException{
       User user = this.repository.getUserByID(id);
       return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return this.repository.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable long id) throws ResourceNotFoundException {

        this.repository.deleteUserByID(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }


    // ---------- SCREENING ROOM ---------- //

    @PostMapping("/rooms")
    public ScreeningRoom createScreeningRoom(@RequestBody ScreeningRoom room) {
        return this.roomRepository.save(room);
    }


    // ---------- RESERVATION ---------- //

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequest request) throws ResourceNotFoundException {

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + request.getUserId()));

        Seat seat = this.seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("No seat exists with ID " + request.getSeatId()));

        Screening screening = this.screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new ResourceNotFoundException("No screening exists with ID " + request.getScreeningId()));

        Reservation reservation = new Reservation(user, seat, screening);

        return this.reservationRepository.save(reservation);
    }

    @GetMapping("reservations/forUser/{id}")
    public Set<Reservation> getReservationsForUser(@PathVariable long id) throws ResourceNotFoundException {
        return this.repository.getReservationsForUser(id);
    }


    // ---------- SEAT ---------- //

    @PostMapping("/seats")
    public Seat createSeat(@RequestBody AddSeatHelper helper) throws ResourceNotFoundException {

        ScreeningRoom room = this.roomRepository.findById(helper.getRoomID())
                .orElseThrow(() -> new ResourceNotFoundException("No screening room exists with ID " + helper.getRoomID()));

        Seat seat = new Seat(helper.getNumber(), helper.getRow(), room);
        return this.seatRepository.save(seat);
    }


    // ---------- SCREENING ---------- //

    @PostMapping("/screenings")
    public Screening createScreeningRoom(@RequestBody AddScreeningHelper helper) throws ResourceNotFoundException {

        ScreeningRoom room = this.roomRepository.findById(helper.getRoomID())
                .orElseThrow(() -> new ResourceNotFoundException("No screening room exists with ID " + helper.getRoomID()));

        Movie movie = this.movieRepository.findById(helper.getMovieID())
                .orElseThrow(() -> new ResourceNotFoundException("No movie exists with ID " + helper.getMovieID()));

        Screening screening = new Screening(helper.getTicketCost(), helper.getDate(), movie, room);
        return this.screeningRepository.save(screening);
    }

    // ---------- MOVIE ---------- //
    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        return this.movieRepository.save(movie);
    }

}
