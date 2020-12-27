package com.example.multiplex.controller;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.*;
import com.example.multiplex.repository.*;
import com.example.multiplex.security.JwtUtil;
import com.example.multiplex.security.MultiplexUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final MultiplexRepository repository;

    private final AuthenticationManager authManager;
    private final MultiplexUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public RestController(MultiplexRepository repository, AuthenticationManager authManager, MultiplexUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.repository = repository;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    // ------------------------------ USER ------------------------------ //
    // ---------- admin-restricted ---------- //
    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return this.repository.getAllUsers();
    }

    @DeleteMapping("/admin/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable long id) throws ResourceNotFoundException {

        this.repository.deleteUserByID(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    // ---------- user-available ---------- //
    // TODO - figure out a way so that a user can only query his own information
    @GetMapping("user/users/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable long id) throws ResourceNotFoundException {
       User user = this.repository.getUserByID(id);
       return ResponseEntity.ok().body(user);
    }

    // ---------- open ---------- //
    // a user without an account must be able to register, so this is unprotected
    @PostMapping("/users")
    public User addUser(@RequestBody AddUserHelper helper) {
        return this.repository.addUser(helper);
    }


    // ------------------------------ SCREENING ROOM ------------------------------ //
    // ---------- admin-restricted ---------- //
    @PostMapping("/admin/rooms")
    public ScreeningRoom addScreeningRoom(@RequestBody ScreeningRoom room) {
        return this.repository.addRoom(room);
    }


    // ------------------------------ RESERVATION ------------------------------ //
    // ---------- admin-restricted ---------- //
    @PostMapping("/admin/reservations")
    public Reservation addReservation(@RequestBody ReservationRequest request) throws ResourceNotFoundException {
        return this.repository.addReservation(request);
    }

    // ---------- user-available ---------- //
    // TODO - figure out a way so that a user can only query his own information
    @GetMapping("/user/reservations/forUser/{id}")
    public Set<Reservation> getReservationsForUser(@PathVariable long id) throws ResourceNotFoundException {
        return this.repository.getReservationsForUser(id);
    }

    @GetMapping("/user/reservations/cost/forUser/{id}")
    public Integer sumReservationsForUser(@PathVariable long id) {
        return this.repository.calculateAllReservations(id);
    }

    @GetMapping("/user/reservations/cost/forUser/{userId}/forScreening/{screeningId}")
    public Integer sumReservationsForUserAndScreening(@PathVariable long userId, @PathVariable long screeningId) {
        return this.repository.calculateReservation(screeningId, userId);
    }


    // ------------------------------ SEAT ------------------------------ //
    // ---------- admin-restricted ---------- //
    @PostMapping("/admin/seats")
    public Seat addSeat(@RequestBody AddSeatHelper helper) throws ResourceNotFoundException {
        return this.repository.addSeat(helper);
    }

    // ---------- user-available ---------- //
    @GetMapping("/user/seats/{id}")
    public List<Seat> getFreeSeatsForScreening(@PathVariable long id) {
        return this.repository.showEmptySeatsForScreening(id);
    }


    // ------------------------------ SCREENING ------------------------------ //
    // ---------- admin-restricted ---------- //
    @PostMapping("/admin/screenings")
    public Screening addScreening(@RequestBody AddScreeningHelper helper) throws ResourceNotFoundException {
        return this.repository.addScreening(helper);
    }

    // ---------- open ---------- //
    @GetMapping("/screenings")
    public List<Screening> getScreeningsOnOffer() {
        return this.repository.getScreeningsOnOffer();
    }


    // ------------------------------ MOVIE ------------------------------ //
    // ---------- admin-restricted ---------- //
    @PostMapping("/admin/movies")
    public Movie addMovie(@RequestBody Movie movie) { return this.repository.addMovie(movie); }

    // ---------- open ---------- //
    @GetMapping("/movies")
    public List<Movie> getMoviesOnOffer() {
        return this.repository.getMoviesOnOffer();
    }

    @GetMapping("/movies/{id}")
    public Movie getMovieDetails(@PathVariable long id) throws ResourceNotFoundException {
        return this.repository.getMovieByID(id);
    }

}
