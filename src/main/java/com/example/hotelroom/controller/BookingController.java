package com.example.hotelroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.service.BookingService;

@RestController

public class BookingController {
	@Autowired
	BookingService bookingService;
	
	//Check room availability for a selected date and room type
//	@PostMapping("/availability/{roomId}")
//	public ResponseEntity<String> checkRoomAvailability(@RequestHeader("userId") String userName,
//			@PathVariable Long roomId, @RequestBody BookingVO bookingVO) {
//		try {
//			String bookingNO = bookingService.checkRoomAvailability(userName, roomId, bookingVO);
//			return ResponseEntity.ok("Room reserved successfully. Booking number is:" + bookingNO);
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
	
	@PostMapping("/availability/{roomId}")
	public ResponseEntity<String> checkRoomAvailability(@RequestHeader("userId") String userName,@PathVariable Long roomId,
	                                          @RequestBody BookingVO bookingVO) {
	    String bookingNO = bookingService.checkRoomAvailability(userName, roomId, bookingVO  );
	    return ResponseEntity.ok("Room reserved successfully. Booking number is: " + bookingNO);
	}
	
	
	//Reserve a room for a specific date and room type
//	@PostMapping("/reserve")
	
//	public ResponseEntity<String> reserveRoom(@RequestHeader("userId") String userName,
//											  @RequestBody BookingVO bookingVO) {
//		String bookingNO=bookingService.reserveRoom(userName,bookingVO);
//		return ResponseEntity.ok("Room reserved successfully. Booking number is:"+bookingNO);
//	}
	
//	public ResponseEntity<String> reserveRoom(@RequestHeader("userId") String userName,
//			  								  @RequestBody BookingVO bookingVO) {
//		try {
//				String bookingNO=bookingService.reserveRoom(userName,bookingVO);
//				return ResponseEntity.ok("Room reserved successfully. Booking number is:"+bookingNO);
//		} catch(IllegalArgumentException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//}

}
