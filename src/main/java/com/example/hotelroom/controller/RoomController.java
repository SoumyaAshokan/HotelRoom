package com.example.hotelroom.controller;

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

import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.vo.RoomVO;
import com.example.hotelroom.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomservice;
	
	/*
	 *get all rooms 
	 */
	@GetMapping("/rooms")
	public List<Room> getRooms() {
		return roomservice.getRooms();
	}
	
	//add a room
	@PostMapping("/rooms")
	public ResponseEntity<String> addRoom(@RequestHeader("userId") Long userId,@RequestBody RoomVO roomVO) {
		roomservice.addRoom(userId,roomVO);
		return ResponseEntity.ok("Room added successfully");
	}
	
	//update a room by id
	@PutMapping("/rooms/{roomId}")
	public ResponseEntity<String> updateRoom(@RequestHeader("userId") Long userId,
											 @PathVariable Long roomId,
											 @RequestBody RoomVO roomVO) {
		roomservice.updateRoom(userId,roomId,roomVO);
		return ResponseEntity.ok("Room updated successfully");
	}
	
	//delete a room by id
	@DeleteMapping("/rooms/{roomId}")
	public ResponseEntity<String> deleteRoom(@RequestHeader("userId") Long userId,@PathVariable Long roomId) {
		roomservice.deleteRoom(userId,roomId);
		return ResponseEntity.ok("Room deleted succesfully");
	}
	
}
