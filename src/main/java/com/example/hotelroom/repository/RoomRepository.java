package com.example.hotelroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
