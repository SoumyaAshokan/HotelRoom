package com.example.hotelroom.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.Booking;
import com.example.hotelroom.service.BookingService;

@RestController
public class BookingController {
	@Autowired
	BookingService bookingService;
	
	//reserve a room
	@PostMapping("reserve")
	public Booking reserveRoom(@RequestParam Long userId,
							   @RequestParam Long roomId,
							   @RequestParam String checkIn,
							   @RequestParam String checkOut,
							   @RequestParam int bookedOccupancy) {
		LocalDate startDate=LocalDate.parse(checkIn);
		LocalDate endDate=LocalDate.parse(checkOut);
		return bookingService.reserveRoom(userId,roomId,startDate,endDate,bookedOccupancy);
	}
}
