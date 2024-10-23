package com.example.hotelroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.exception.HotelCustomException;
import com.example.hotelroom.model.vo.BookResponseVO;
import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.service.BookingService;

@RestController

public class BookingController {
	@Autowired
	BookingService bookingService;

	private static final String USER_ID = "userId";

	/**
	 * Check room availability for a selected date and room type and reserve a room
	 * 
	 * @param userName
	 * @param bookingVO
	 * @return
	 */
	@PostMapping("/reserveBook")
	public ResponseEntity<?> checkRoomAvailability(@RequestHeader(USER_ID) String userName,
			@RequestBody BookingVO bookingVO) {
		try {
			BookResponseVO responseVO = bookingService.checkRoomAvailability(userName, bookingVO);
			return ResponseEntity.ok(responseVO);
		} catch (HotelCustomException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	/**
	 * cancel a reservation
	 * 
	 * @param userName
	 * @param bookingVO
	 * @return
	 */
	@PostMapping("/cancelBooking")
	public ResponseEntity<String> cancelBooking(@RequestHeader(USER_ID) String userName,
			@RequestBody BookingVO bookingVO) {
		try {
			String result = bookingService.cancelBooking(userName, bookingVO.getBookingNo());
			return ResponseEntity.ok(result);
		} catch (HotelCustomException e) {
			return ResponseEntity.ok(e.getMessage());
		}

	}

	/**
	 * Modify an existing reservation by adding more guests
	 * 
	 * @param userName
	 * @param bookingNo
	 * @param additionalGuest
	 * @return
	 */
	@PostMapping("/updateGuests")
	public ResponseEntity<String> updateBooking(@RequestHeader(USER_ID) String userName,
			@RequestBody BookingVO bookingVO) {
		try {
			String result = bookingService.updateBooking(userName, bookingVO.getBookingNo(),
					bookingVO.getAdditionalGuest());
			return ResponseEntity.ok(result);
		} catch (HotelCustomException e) {
			return ResponseEntity.ok(e.getMessage());
		}

	}

	/**
	 * view booking
	 * 
	 * @param viewVO
	 * @return
	 */
	@PostMapping("/viewBooking")
	public ResponseEntity<List<BookingVO>> viewAllBookings(@RequestBody BookingVO bookingVO) {
		List<BookingVO> bookings = bookingService.viewAllBookings(bookingVO);
		return ResponseEntity.ok(bookings);
	}

}
