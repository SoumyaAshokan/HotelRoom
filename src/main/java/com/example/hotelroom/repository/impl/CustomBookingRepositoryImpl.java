package com.example.hotelroom.repository.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Repository;

import com.example.hotelroom.repository.CustomBookingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomBookingRepositoryImpl implements CustomBookingRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public int isRoomAvailable(Long roomId,LocalDate checkIn, LocalDate checkOut) {
		String jpql = "SELECT COUNT(b) FROM Booking b WHERE b.room.roomId = :roomId " + "AND (:checkIn < b.checkOut  AND :checkOut > b.checkIn)"
						+ "AND b.status = true";
		
		TypedQuery<Long> query = entityManager.createQuery(jpql,Long.class);
		query.setParameter("roomId", roomId);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);
		
		return query.getSingleResult().intValue();

	}

}
