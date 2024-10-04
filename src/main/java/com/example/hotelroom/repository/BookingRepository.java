package com.example.hotelroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

}
