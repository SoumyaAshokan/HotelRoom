package com.example.hotelroom.controller;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.service.BookingService;

@RestController

public class BookingController {
	@Autowired
	BookingService bookingService;
	
	
	//Check room availability for a selected date and room type
	@GetMapping("/availability")
	public boolean isRoomAvailable(@RequestParam Long roomId,
								   @RequestParam String checkIn,
								   @RequestParam String checkOut) {
		LocalDate startDate=LocalDate.parse(checkIn);
		LocalDate endDate=LocalDate.parse(checkOut);
		return bookingService.isRoomAvailable(roomId,startDate,endDate);
	}
}
