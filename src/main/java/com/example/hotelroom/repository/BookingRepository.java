package com.example.hotelroom.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

	Optional<Booking> findByBookingNo(String bookingNo);
	List<Booking> findByStatus(boolean status);
	List<Booking> findByRoomCategory(String category);
	List<Booking> findByUserUserName(String userName);
	


}
