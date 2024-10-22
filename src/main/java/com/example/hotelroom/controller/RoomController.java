package com.example.hotelroom.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.exception.HotelCustomException;
import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.vo.RoomAvailabilityVO;
import com.example.hotelroom.model.vo.RoomVO;
import com.example.hotelroom.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomservice;

	private static final String USER_ID = "userId";

	/**
	 * get all rooms
	 * 
	 * @return
	 */
	@GetMapping("/rooms")
	public List<Room> getRooms() {
		return roomservice.getRooms();
	}

	/**
	 * add a room
	 * 
	 * @param userName
	 * @param roomVO
	 * @return
	 */
	@PostMapping("/rooms")
	public ResponseEntity<String> addRoom(@RequestHeader(USER_ID) String userName, @RequestBody RoomVO roomVO) {
		try {
			roomservice.addRoom(userName, roomVO);
			return ResponseEntity.ok("Room added successfully");
		} catch (HotelCustomException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}

	/**
	 * update a room by id
	 * 
	 * @param userName
	 * @param roomVO
	 * @return
	 */
	@PutMapping("/rooms")
	public ResponseEntity<String> updateRoom(@RequestHeader(USER_ID) String userName, @RequestBody RoomVO roomVO) {
		try {
			roomservice.updateRoom(userName, roomVO);
			return ResponseEntity.ok("Room updated successfully");
		} catch (HotelCustomException e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}

	/**
	 * delete a room by id
	 * 
	 * @param userName
	 * @param roomId
	 * @return
	 */
	@DeleteMapping("/rooms/{roomId}")
	public ResponseEntity<String> deleteRoom(@RequestHeader(USER_ID) String userName, @PathVariable Long roomId) {
		try {
			roomservice.deleteRoom(userName, roomId);
			return ResponseEntity.ok("Room deleted succesfully");
		} catch (HotelCustomException e) {
			return ResponseEntity.ok(e.getMessage());
		}

	}

	/**
	 * availability of rooms
	 * 
	 * @param userName
	 * @param availabilityVO
	 * @return
	 */
	@PostMapping("/searchRoom")
	public ResponseEntity<String> searchBooking(@RequestHeader(USER_ID) String userName,
			@RequestBody RoomAvailabilityVO availabilityVO) {

		LocalDate checkIn = availabilityVO.getCheckIn();
		LocalDate checkOut = availabilityVO.getCheckOut();
		Integer bookedOccupancy = availabilityVO.getBookedOccupancy();
		String category = availabilityVO.getCategory();

		String response = roomservice.searchBooking(checkIn, checkOut, bookedOccupancy, category);
		return ResponseEntity.ok(response);
	}

}
