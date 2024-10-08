package com.example.hotelroom.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.Booking;
import com.example.hotelroom.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepo;


	//Check room availability for a selected date and room type
	public boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
		List<Booking> existingBooking=bookingRepo.findByroomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(roomId,startDate,endDate);
		return existingBooking.isEmpty();
	}
	
	
}
