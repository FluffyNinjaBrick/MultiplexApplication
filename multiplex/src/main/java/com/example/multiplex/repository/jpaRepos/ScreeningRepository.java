package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
