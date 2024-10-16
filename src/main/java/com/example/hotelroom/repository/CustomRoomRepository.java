package com.example.hotelroom.repository;

import java.time.LocalDate;
import java.util.List;

public interface CustomRoomRepository {

	List<Object[]> searchBooking(LocalDate checkIn, LocalDate checkOut, Integer bookedOccupancy, String category);

}
