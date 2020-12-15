package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Reservation;
import com.example.multiplex.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
    value = "select u from User u JOIN FETCH u.reservations r JOIN FETCH r.screening s JOIN FETCH s.movie m WHERE u.id = :id_num")
    User getUserReservationsWithTitle(@Param("id_num") long id);


}
