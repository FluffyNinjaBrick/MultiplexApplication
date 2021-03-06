package com.example.multiplex.repository.jpaRepos;

import com.example.multiplex.model.persistence.ScreeningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
}
