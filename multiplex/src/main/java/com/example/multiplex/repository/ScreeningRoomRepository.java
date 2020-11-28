package com.example.multiplex.repository;

import com.example.multiplex.model.ScreeningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
}
