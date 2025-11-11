package com.ptit.hotelmanagementsystem.repository;

import com.ptit.hotelmanagementsystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
