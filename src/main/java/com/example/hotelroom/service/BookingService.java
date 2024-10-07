package com.example.hotelroom.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.Booking;
import com.example.hotelroom.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingrepo;

	//reserve a room
	public Booking reserveRoom(Long userId, Long roomId, LocalDate startDate, LocalDate endDate, int bookedOccupancy) {
		return null;
	}
	
}
