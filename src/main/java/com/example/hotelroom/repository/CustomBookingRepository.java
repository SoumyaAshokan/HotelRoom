package com.example.hotelroom.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotelroom.model.entity.Booking;

public interface CustomBookingRepository {
	Map<Long, String> checkRoomAvailability(LocalDate checkIn, LocalDate checkOut,String category, int bookedOccupancy);
	
	List<Booking> searchBookings(Boolean status, String bookingNo, String category, String userName);


	int getCurrentOccupancy (String roomNo, LocalDate checkIn,  LocalDate checkOut);
}



