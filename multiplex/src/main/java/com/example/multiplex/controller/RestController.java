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

    private final MultiplexRepository repository;

    @Autowired
    public RestController(MultiplexRepository repository) {
        this.repository = repository;
    }

    // ---------- AUTH TEST ----------

    @GetMapping("/test")
    public String home() {
        return "<h1>Welcome<h1>";
    }

    @GetMapping("/admin")
    public String homeAdmin() {
        return "<h1>Welcome admin<h1>";
    }

    @GetMapping("/user")
    public String homeUser() {
        return "<h1>Welcome user<h1>";
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
        return this.repository.addRoom(room);
    }


    // ---------- RESERVATION ---------- //

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequest request) throws ResourceNotFoundException {
        return this.repository.addReservation(request);
    }

    @GetMapping("reservations/forUser/{id}")
    public Set<Reservation> getReservationsForUser(@PathVariable long id) throws ResourceNotFoundException {
        return this.repository.getReservationsForUser(id);
    }


    // ---------- SEAT ---------- //

    @PostMapping("/seats")
    public Seat createSeat(@RequestBody AddSeatHelper helper) throws ResourceNotFoundException {
        return this.repository.addSeat(helper);
    }


    // ---------- SCREENING ---------- //

    @PostMapping("/screenings")
    public Screening createScreening(@RequestBody AddScreeningHelper helper) throws ResourceNotFoundException {
        return this.repository.addScreening(helper);
    }

    // ---------- MOVIE ---------- //
    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) { return this.repository.addMovie(movie); }

}
