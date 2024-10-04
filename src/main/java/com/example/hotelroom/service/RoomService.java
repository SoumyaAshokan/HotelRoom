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
	public void addRoom(Room room) {
		roomrepo.save(room);
	}

	public void updateroom(Long room_id, Room uproom) {
		
		
	}
}
