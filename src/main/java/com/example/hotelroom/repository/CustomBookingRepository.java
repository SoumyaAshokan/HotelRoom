package com.example.hotelroom.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotelroom.model.entity.Booking;

public interface CustomBookingRepository extends JpaRepository<Booking,Long> {
	
	@Query(value = "SELECT COUNT(*)>0 FROM bookings " +
            "WHERE room_id = ?1 " +
            "AND ((check_in < ?2 AND check_out > ?2) OR (check_in < ?3 AND check_out > ?3))", 
    nativeQuery = true)
	boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);

}
