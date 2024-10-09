package com.example.hotelroom.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

	List<Booking> findByRoomRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(Long roomId, LocalDate startDate,LocalDate endDate);
}
