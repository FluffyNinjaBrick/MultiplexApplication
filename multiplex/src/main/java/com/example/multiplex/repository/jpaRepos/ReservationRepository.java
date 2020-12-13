package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(
            value= "select sum(ticket_cost) from reservations r inner join screenings s on r.screening_id = s.id where screening_id = :screening_id and user_id = :user_id",
            nativeQuery = true)
    Integer calculateTotalReservationCost(@Param("screening_id") int screening_id, @Param("user_id") int user_id);

    @Query(
            value= "select sum(ticket_cost) from reservations r inner join screenings s on r.screening_id = s.id where user_id = :user_id",
            nativeQuery = true)
    Integer calculateTotalReservationCost(@Param("user_id") int user_id);
}
