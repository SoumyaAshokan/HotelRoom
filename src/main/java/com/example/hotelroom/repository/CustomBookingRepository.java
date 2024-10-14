package com.example.hotelroom.repository;

import java.time.LocalDate;
import java.util.Map;

public interface CustomBookingRepository {
	Map<Long, String> checkRoomAvailability(LocalDate checkIn, LocalDate checkOut,String category, int bookedOccupancy);
}

//	@Query(value = "SELECT COUNT(*)=0 FROM bookings " +
//            "WHERE room_id = ?1 " +
//            "AND ( ?2 < check_out AND ?3 > check_in )", 
//    nativeQuery = true)


