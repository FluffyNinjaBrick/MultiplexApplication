package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
