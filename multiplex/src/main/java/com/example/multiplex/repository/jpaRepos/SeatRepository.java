package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query(
            value = "(select s.id, s.row_number, s.seat_number, s.screening_room_id from seats s inner join screening_rooms rs on s.screening_room_id = rs.id inner join screenings sc on rs.id = sc.screening_room_id where sc.id = :screening_id) " +
                    "EXCEPT (select s.id, s.row_number, s.seat_number, s.screening_room_id from seats s inner join reservations r on s.id = r.seat_id where r.screening_id = :screening_id)",
            nativeQuery = true)
    List<Seat> showEmptySeats(@Param("screening_id") long screening_id);
}
