package com.example.multiplex.controller;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.ReservationRequest;
import com.example.multiplex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserRepository userRepository;
    private final ScreeningRoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public RestController(UserRepository userRepository, ScreeningRoomRepository roomRepository, ReservationRepository reservationRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
    }


    // ---------- USER ---------- //

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable long id) throws ResourceNotFoundException{

       User user = this.userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + id));

       return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable long id) throws ResourceNotFoundException {

        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + id));

        this.userRepository.delete(user);

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
                .orElseThrow(() -> new ResourceNotFoundException("No seat exists with ID " + request.getScreeningId()));

        Reservation reservation = new Reservation(user, seat, screening);

        return this.reservationRepository.save(reservation);
    }


    // ---------- SEAT ---------- //

    @PostMapping("/seats")
    public Seat createSeat(@RequestBody Seat seat) {
        return this.seatRepository.save(seat);
    }


    // ---------- SCREENING ----------

    @PostMapping("/screenings")
    public Screening createScreeningRoom(@RequestBody Screening screening) {
        return this.screeningRepository.save(screening);
    }
}
