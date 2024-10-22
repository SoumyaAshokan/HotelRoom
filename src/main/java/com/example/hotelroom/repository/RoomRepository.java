package com.example.hotelroom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	Optional<Room> findByRoomNo(String roomNo);

	List<Room> findByCategoryAndCapacityGreaterThanEqual(String category, int capacity);

}
