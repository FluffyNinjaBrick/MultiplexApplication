package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
