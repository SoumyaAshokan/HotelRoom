package com.example.hotelroom.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.vo.BookResponseVO;
import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.model.vo.RoomAvailabilityVO;
import com.example.hotelroom.service.BookingService;
import com.example.hotelroom.service.RoomService;

@RestController

public class BookingController {
	@Autowired
	BookingService bookingService;
	
	//Check room availability for a selected date and room type and reserve a room
	@PostMapping("/reserveBook")
	public ResponseEntity<BookResponseVO> checkRoomAvailability(@RequestHeader("userId") String userName,
	                                          @RequestBody BookingVO bookingVO) {
		BookResponseVO responseVO = bookingService.checkRoomAvailability(userName, bookingVO);
	    return ResponseEntity.ok(responseVO);
	}
	
	//cancel a reservation
	@DeleteMapping("/cancelBooking")
	public ResponseEntity<String> cancelBooking(@RequestHeader("userId") String userName,
								                @RequestBody String bookingNo) {
		String result=bookingService.cancelBooking(userName,bookingNo);
		return ResponseEntity.ok(result);
	}

	//Modify an existing reservation by adding more guests
	@PatchMapping("/updateGuests")
	public ResponseEntity<String> updateBooking(@RequestHeader("userId") String userName,
			      								@RequestParam String bookingNo,
			      								@RequestParam int additionalGuest) {
		String result = bookingService.updateBooking(userName,bookingNo,additionalGuest);
		return ResponseEntity.ok(result);
	}
	
	//View all reservations
	@GetMapping("/all")
	public ResponseEntity<List<BookingVO>> viewAllReservations() {
		List<BookingVO> bookings = bookingService.viewAllReservations();
		return ResponseEntity.ok(bookings);
	}
}
