package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
