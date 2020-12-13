package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
    value = "select u from User u JOIN FETCH u.reservations WHERE u.firstName = :first_name")
    User myFunction(@Param("first_name") String firstName);
}
