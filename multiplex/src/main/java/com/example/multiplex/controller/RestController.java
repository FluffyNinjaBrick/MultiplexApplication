package com.example.multiplex.controller;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.ScreeningRoom;
import com.example.multiplex.model.User;
import com.example.multiplex.repository.ScreeningRoomRepository;
import com.example.multiplex.repository.UserRepository;
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
    private final ScreeningRoomRepository screeningRoomRepository;

    @Autowired
    public RestController(UserRepository userRepository, ScreeningRoomRepository screeningRoomRepository) {
        this.userRepository = userRepository;
        this.screeningRoomRepository = screeningRoomRepository;
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
        return this.screeningRoomRepository.save(room);
    }

}
