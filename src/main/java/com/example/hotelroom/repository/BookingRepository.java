package com.example.hotelroom.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.model.entity.Room;

public interface BookingRepository extends JpaRepository<Booking,Long>{

	Optional<Booking> findByBookingNo(String bookingNo);

}
