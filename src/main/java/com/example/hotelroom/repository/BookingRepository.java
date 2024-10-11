package com.example.hotelroom.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelroom.model.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

}
