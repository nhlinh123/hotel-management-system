package com.ptit.hotelmanagementsystem.repository;

import com.ptit.hotelmanagementsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
