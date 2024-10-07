package com.example.hotelroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelroom.model.Room;
import com.example.hotelroom.service.RoomService;

@RestController
public class RoomController {

	@Autowired
	RoomService roomservice;
	
	@GetMapping("/rooms")
	public List<Room> getRooms() {
		return roomservice.getRooms();
	}
	
	//add a room
	@PostMapping("/rooms") 
	public Room addRoom(@RequestBody Room room) {
		return roomservice.addRoom(room);
	}
	
	//update a room by id
	@PutMapping("/rooms/{roomId}")
	public Room updateRoom(@PathVariable Long roomId,@RequestBody Room uproom) {
		return roomservice.updateRoom(roomId,uproom);
	}
	
	//delete a room by id
	@DeleteMapping("/rooms/{roomId}")
	public void deleteRoom(@PathVariable Long roomId) {
		roomservice.deleteRoom(roomId);
	}
}
