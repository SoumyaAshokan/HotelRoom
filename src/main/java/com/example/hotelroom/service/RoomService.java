package com.example.hotelroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.entity.User;
import com.example.hotelroom.model.vo.RoomVO;
import com.example.hotelroom.repository.RoomRepository;
import com.example.hotelroom.repository.UserRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepo;
	@Autowired
	UserRepository userRepo;

	//get all rooms
	public List<Room> getRooms() {
		return roomRepo.findAll();
	}
	
	//add a room
	public void addRoom(String userName, RoomVO roomVO) {
		User user=getUserByName(userName);
		if(!user.getRole().equalsIgnoreCase("Admin")) {
			throw new IllegalArgumentException("Only admin can add rooms.");
		}
		
		Room room=convertToEntity(roomVO);
		roomRepo.save(room);	
	}

	//update a room by id
	public void updateRoom(String userName, Long roomId, RoomVO roomVO) {
		User user=getUserByName(userName);
		if(!user.getRole().equalsIgnoreCase("Admin")) {
			throw new IllegalArgumentException("Only admin can update rooms.");
		}
		
		Room existingRoom=roomRepo.findById(roomId)
								  .orElseThrow(()-> new IllegalArgumentException("Room with id" +roomId+ " is not found"));
		existingRoom.setRoomNo(roomVO.getRoomNo());
		existingRoom.setCategory(roomVO.getCategory());
		existingRoom.setCapacity(roomVO.getCapacity());
		roomRepo.save(existingRoom);
	}
	
	//delete a room by id
	public void deleteRoom(String userName, Long roomId) {
		User user=getUserByName(userName);
		if(!user.getRole().equalsIgnoreCase("Admin")) {
			throw new IllegalArgumentException("Only admin can delete rooms");
		}
		
		if(roomRepo.existsById(roomId)) {
			roomRepo.deleteById(roomId);
		} else {
			throw new IllegalArgumentException("Room with id\" +roomId+ \" is not found");
		}
	}
	

	//convert RoomVO to Room Entity
	private Room convertToEntity(RoomVO roomVO) {
		Room room=new Room();
		room.setRoomId(roomVO.getRoomId());
		room.setRoomNo(roomVO.getRoomNo());
		room.setCategory(roomVO.getCategory());
		room.setCapacity(roomVO.getCapacity());
		return room;	
	}
	
	//convert Room Entity to RoomVO
	private RoomVO convertToVO(Room room) {
		RoomVO roomVO=new RoomVO();
		roomVO.setRoomId(room.getRoomId());
		roomVO.setRoomNo(room.getRoomNo());
		roomVO.setCategory(room.getCategory());
		roomVO.setCapacity(room.getCapacity());
		return roomVO;
	}
	
	private User getUserByName(String userName) {
			return userRepo.findByUserName(userName)
					       .orElseThrow(()-> new IllegalArgumentException("User not found"));
		}
}
