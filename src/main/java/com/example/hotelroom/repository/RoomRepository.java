package com.example.hotelroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {
	Optional<Room> findByRoomNo(String roomNo);

}
