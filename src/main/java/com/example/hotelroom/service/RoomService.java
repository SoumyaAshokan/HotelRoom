package com.example.hotelroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.Room;
import com.example.hotelroom.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomrepo;

	public List<Room> getRooms() {
		return roomrepo.findAll();
	}
	
	//add a room
	public Room addRoom(Room room) {
		return roomrepo.save(room);
	}

	//update a room by id
	public Room updateRoom(Long roomId, Room uproom) {
		Room existingRoom=roomrepo.findById(roomId)
								  .orElseThrow(() -> new IllegalArgumentException("Room with id" +roomId+ " is not found"));
		existingRoom.setRoomNo(uproom.getRoomNo());
		existingRoom.setCategory(uproom.getCategory());
		existingRoom.setCapacity(uproom.getCapacity());
		return roomrepo.save(existingRoom);
	}

	//delete a room by id
	public void deleteRoom(Long roomId) {
		if(roomrepo.existsById(roomId)) {
			roomrepo.deleteById(roomId);
		} else {
			throw new IllegalArgumentException("Room with id" +roomId+ " is not found");
		}
	}
	
}
