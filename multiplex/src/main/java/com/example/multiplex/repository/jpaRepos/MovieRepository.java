package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(
            value= "select distinct m.id, m.author, m.description, m.title " +
                    "from screenings s " +
                    "inner join movies m on s.movie_id = m.id " +
                    "where s.date > :now",
            nativeQuery = true)
//    @Query( value = "select m from Movie m join ")
    List<Movie> getMoviesOnOffer(@Param("now") Date now);

}
