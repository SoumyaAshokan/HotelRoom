package com.example.hotelroom.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.service.BookingService;

@RestController

public class BookingController {
	@Autowired
	BookingService bookingService;
	
	//Check room availability for a selected date and room type
	//@GetMapping("/availability")
//	public boolean isRoomAvailable(@RequestParam Long roomId,
//								   @RequestParam String checkIn,
//								   @RequestParam String checkOut) {
//		LocalDate startDate=LocalDate.parse(checkIn);
//		LocalDate endDate=LocalDate.parse(checkOut);
//		return bookingService.isRoomAvailable(roomId,startDate,endDate);
//	}
}
