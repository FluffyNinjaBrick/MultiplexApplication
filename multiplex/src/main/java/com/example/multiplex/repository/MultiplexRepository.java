package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.Reservation;
import com.example.multiplex.model.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository("HibernateRepository")
public class MultiplexRepository implements IMultiplexRepository {

    @PersistenceContext
    private EntityManager em;

    private final UserRepository userRepository;
    private final ScreeningRoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public MultiplexRepository(UserRepository userRepository,
                               ScreeningRoomRepository roomRepository,
                               ReservationRepository reservationRepository,
                               SeatRepository seatRepository,
                               ScreeningRepository screeningRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
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


    // ----------- RESERVATION -----------
    public Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + userID));
        return user.getReservations();
    }


}
