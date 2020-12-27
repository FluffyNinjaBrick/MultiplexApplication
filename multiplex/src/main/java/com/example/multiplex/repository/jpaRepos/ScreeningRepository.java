package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query(
            value= "select * from screenings s where s.date > :now",
            nativeQuery = true)
    List<Screening> getScreeningsOnOffer(@Param("now") Date now);
}
