package com.example.hotelroom.repository;

import java.time.LocalDate;

public interface CustomBookingRepository {
	int isRoomAvailable(Long roomId,LocalDate checkIn, LocalDate checkOut);
}

//	@Query(value = "SELECT COUNT(*)=0 FROM bookings " +
//            "WHERE room_id = ?1 " +
//            "AND ( ?2 < check_out AND ?3 > check_in )", 
//    nativeQuery = true)


