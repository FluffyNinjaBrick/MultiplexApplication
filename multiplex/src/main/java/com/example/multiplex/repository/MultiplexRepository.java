package com.example.multiplex.repository;

import com.example.multiplex.exceptions.ResourceNotFoundException;
import com.example.multiplex.model.persistence.*;
import com.example.multiplex.model.util.AddScreeningHelper;
import com.example.multiplex.model.util.AddSeatHelper;
import com.example.multiplex.model.util.AddUserHelper;
import com.example.multiplex.model.util.ReservationRequest;
import com.example.multiplex.repository.jpaRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    private final RoleRepository roleRepository;

    @Autowired
    public MultiplexRepository(UserRepository userRepository,
                               ScreeningRoomRepository roomRepository,
                               ReservationRepository reservationRepository,
                               SeatRepository seatRepository,
                               ScreeningRepository screeningRepository, MovieRepository movieRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roleRepository = roleRepository;
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
    public User getUserByUsername(String username) throws ResourceNotFoundException {
        List<User> allUsers = this.getAllUsers();
        for (User u: allUsers) if (u.getUsername().equals(username)) return u;
        throw new ResourceNotFoundException("No user exists with username " + username);
    }

    @Override
    public User addUser(AddUserHelper helper) {
        User user = new User(
                helper.getFirstName(),
                helper.getLastName(),
                helper.getEmail(),
                helper.getUsername(),
                helper.getPassword()
        );
        user.addRole(this.roleRepository.findById("USER").get());
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

    private ScreeningRoom getRoomByNumber(String roomNumber) throws ResourceNotFoundException {
        ScreeningRoom room = null;

        for (ScreeningRoom r: this.roomRepository.findAll()) {
            if (r.getNumber().equals(roomNumber)) {
                room = r;
                break;
            }
        }

        if (room == null) throw new ResourceNotFoundException("Error: no room exists with number " + roomNumber);
        return room;
    }


    // ----------- RESERVATION -----------
    @Override
    public Reservation addReservation(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    @Override
    public Reservation addReservation(ReservationRequest request) throws ResourceNotFoundException {
        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + request.getUserId()));
        Screening screening = this.screeningRepository.findById(request.getScreeningId())
                .orElseThrow(() -> new ResourceNotFoundException("No screening exists with ID " + request.getScreeningId()));

        long screeningRoomID = screening.getScreeningRoom();
        Seat seat = this.getSeatByNumRowRoom(request.getSeatNumber(), request.getRowNumber(), screeningRoomID);

        Reservation reservation = new Reservation(user, seat, screening);
        return this.reservationRepository.save(reservation);
    }

    @Override
    public Set<Reservation> getReservationsForUser(long userID) throws ResourceNotFoundException {
        User user = this.userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("No user exists with ID " + userID));
        return user.getReservations();
    }

    @Override
    public Integer calculateReservation(long screening_id, long user_id) {
        return this.reservationRepository.calculateTotalReservationCost(screening_id, user_id);
    }

    @Override
    public Integer calculateAllReservations(long user_id){
        return this.reservationRepository.calculateTotalReservationCost(user_id);
    }

    // guys, what is this?
    public Set<Reservation> getReservationsForUserWithTitle(long userID) throws ResourceNotFoundException {
        User user = this.userRepository.getUserReservationsWithTitle(userID);
//        user.getReservations().forEach((r) -> {
//            System.out.println(r.getScreening().getMovie())
//        });
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

    @Override
    public Seat addSeat(AddSeatHelper helper) throws ResourceNotFoundException {
        ScreeningRoom room = this.getRoomByNumber(helper.getRoomNumber());
        Seat seat = new Seat(helper.getNumber(), helper.getRow(), room);
        return this.seatRepository.save(seat);
    }

    @Override
    public List<Seat> showEmptySeatsForScreening(long screening_id){
        return this.seatRepository.showEmptySeats(screening_id);
    }

    private Seat getSeatByNumRowRoom(int number, int row, long roomID) throws ResourceNotFoundException {
        for (Seat s: this.seatRepository.findAll())
            if (s.getSeatNumber() == number && s.getRowNumber() == row && s.getScreeningRoom() == roomID)
                return s;
        throw new ResourceNotFoundException("No such seat exists");
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

    @Override
    public Screening addScreening(AddScreeningHelper helper) throws ResourceNotFoundException {
        String movieTitle = helper.getMovieTitle();
        String roomNumber = helper.getRoomNumber();

        // find requested movie and room
        Movie movie = this.getMovieByTitle(movieTitle);
        ScreeningRoom room = this.getRoomByNumber(roomNumber);

        // create and save screening
        Screening screening = new Screening(helper.getTicketCost(), helper.getDate(), movie, room);
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
    @Override
    public List<Screening> getScreeningsOnOffer() {
        return this.screeningRepository.getScreeningsOnOffer(new Date());
    }
    private Movie getMovieByTitle(String title) throws ResourceNotFoundException {
        Movie movie = null;

        for (Movie m: this.movieRepository.findAll()) {
            if (m.getTitle().equals(title)) {
                movie = m;
                break;
            }
        }

        if (movie == null) throw new ResourceNotFoundException("Error: no movie exists with title " + title);
        return movie;
    }

    @Override
    public List<Movie> getMoviesOnOffer() {
        return this.movieRepository.getMoviesOnOffer(new Date());
    }

}
